package com.szh.sms.mapper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreAndTimetableMapperTest {

  @Test
  void studentScoreQueriesUseRecordedCourseYearAndTerm() throws IOException {
    String mapper = read("src/main/resources/mapper/Score/ScoreMapper.xml");

    String exportListByStudent = selectBody(mapper, "getExportListByStudent");
    String studentTotal = selectBody(mapper, "getStudentTotal");

    assertTrue(exportListByStudent.contains("a.year = #{condition.year}"));
    assertTrue(exportListByStudent.contains("a.term = #{condition.term}"));
    assertTrue(studentTotal.contains("a.year = #{condition.year}"));
    assertTrue(studentTotal.contains("a.term = #{condition.term}"));
  }

  @Test
  void teacherTimetableCarriesYearIntoWeekCourseQuery() throws IOException {
    String timetableService = read("src/main/java/com/szh/sms/service/Timetable/impl/TimetableServiceImpl.java");
    String timetableView = read("sms/src/components/timetable/timetable.vue");

    String teacherMethod = methodBody(timetableService, "getTimetableByTeacher");

    assertTrue(timetableView.contains("year: this.form.year"));
    assertTrue(teacherMethod.contains("map.put(\"year\", condition.get(\"year\"))"));
  }

  private String read(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
  }

  private String selectBody(String xml, String id) {
    Pattern pattern = Pattern.compile("<select id=\"" + id + "\"[\\s\\S]*?</select>");
    Matcher matcher = pattern.matcher(xml);
    assertTrue(matcher.find());
    return matcher.group();
  }

  private String methodBody(String javaSource, String methodName) {
    int methodStart = javaSource.indexOf(methodName + "(");
    assertTrue(methodStart >= 0);
    int bodyStart = javaSource.indexOf("{", methodStart);
    int depth = 0;
    for (int i = bodyStart; i < javaSource.length(); i++) {
      char current = javaSource.charAt(i);
      if (current == '{') {
        depth++;
      } else if (current == '}') {
        depth--;
        if (depth == 0) {
          return javaSource.substring(bodyStart, i + 1);
        }
      }
    }
    throw new IllegalArgumentException("Method body not found: " + methodName);
  }
}
