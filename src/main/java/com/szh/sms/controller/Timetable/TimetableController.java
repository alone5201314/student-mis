package com.szh.sms.controller.Timetable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szh.sms.dao.Course.CourseMapper;
import com.szh.sms.domain.CourseInfo;
import com.szh.sms.domain.WeekCourse;
import com.szh.sms.dto.Course;
import com.szh.sms.service.Timetable.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Description timetable controller
 **/
@RestController
@RequestMapping("/api/sms/timetable")
public class TimetableController {
  @Autowired
  private TimetableService timetableService;
  @Autowired
  private CourseMapper courseMapper;

  @PostMapping
  public void add(@RequestBody JSONArray WeekCourseList) {
    List<WeekCourse> list = JSONObject.parseArray(WeekCourseList.toJSONString(), WeekCourse.class);
    timetableService.add(list);
  }

  @GetMapping("/getTimetable")
  public List<WeekCourse> getStudentList(@RequestParam Map<String, Object> condition) {
    return timetableService.getTimetable(condition);
  }

  @GetMapping("/getTimetableByStudent")
  public List<WeekCourse> getTimetableByStudent(@RequestParam Map<String, Object> condition) {
    return timetableService.getTimetableByStudent(condition);
  }

  @GetMapping("/getTimetableByTeacher")
  public List<WeekCourse> getTimetableByTeacher(@RequestParam Map<String, Object> condition) {
    return timetableService.getTimetableByTeacher(condition);
  }

  @PostMapping("/updateCourseInfo")
  public void updateCourseInfo(@RequestBody CourseInfo courseInfo) {
    timetableService.updateCourseInfo(courseInfo);
  }

  @PostMapping("/generateRandom")
  public void generateRandomTimetable(
      @RequestParam String profession,
      @RequestParam String grade,
      @RequestParam Integer year,
      @RequestParam Integer term) {
    List<Course> courses = getCoursesForRandom(profession, term);
    List<WeekCourse> list = new ArrayList<>();
    Random random = new Random();
    String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};

    for (int i = 0; i < 10; i++) {
      WeekCourse weekCourse = new WeekCourse();
      weekCourse.setProfession(profession);
      weekCourse.setGrade(grade);
      weekCourse.setYear(year);
      weekCourse.setTerm(term);
      weekCourse.setSaturday("一");
      weekCourse.setSunday("一");

      for (String day : days) {
        String courseName = random.nextDouble() < 0.6
            ? courses.get(random.nextInt(courses.size())).getName()
            : "一";
        setCourseByDay(weekCourse, day, courseName);
      }
      list.add(weekCourse);
    }

    timetableService.add(list);
  }

  private List<Course> getCoursesForRandom(String profession, Integer term) {
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", profession);
    condition.put("term", term);
    List<Course> courses = courseMapper.getCourseByMap(condition);

    if (courses.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "No course found for the selected profession and term");
    }
    return courses;
  }

  private void setCourseByDay(WeekCourse weekCourse, String day, String courseName) {
    switch (day) {
      case "monday":
        weekCourse.setMonday(courseName);
        break;
      case "tuesday":
        weekCourse.setTuesday(courseName);
        break;
      case "wednesday":
        weekCourse.setWednesday(courseName);
        break;
      case "thursday":
        weekCourse.setThursday(courseName);
        break;
      case "friday":
        weekCourse.setFriday(courseName);
        break;
      default:
        break;
    }
  }
}
