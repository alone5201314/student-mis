package com.szh.sms.controller.Score;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szh.sms.dto.Course;
import com.szh.sms.dto.Score;
import com.szh.sms.dto.User;
import com.szh.sms.service.MailService;
import com.szh.sms.service.Score.ScoreService;
import com.szh.sms.service.User.UserService;
import com.szh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Description 成绩查询控制层
 **/

@RestController
@RequestMapping("/api/sms/score")
public class ScoreController {

  @Autowired
  private ScoreService scoreService;

  @Autowired
  private UserService userService;

  @Autowired
  private MailService mailService;

  @GetMapping("/getCourseList")
  public PagingResult<Course> getCourseList (@RequestParam Map<String, Object> condition,
                                             @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                             @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return scoreService.getCourseList(rowBounds, condition);
  }
  @PostMapping
  private void addEntry(@RequestBody JSONArray UserScore) {
    List<Score> list = JSONObject.parseArray(UserScore.toJSONString(), Score.class);
    scoreService.addEntry(list);
  }
  @GetMapping("/export")
  public List<Course> getExportList (@RequestParam Map<String, Object> condition) {
    return scoreService.getExportList(condition);
  }
  @GetMapping("/getUserNum")
  public List<Map<String, Object>> getUserNum (@RequestParam Map<String, Object> condition) {
    return scoreService.getUserNum(condition);
  }

  @GetMapping("/getUserTotal")
  public Map<String, Object> getUserTotal (@RequestParam Map<String, Object> condition) {
    return scoreService.getUserTotal(condition);
  }

  @PostMapping("/sendEmail")
  public Map<String, Object> sendScoreEmail(@RequestBody Map<String, Object> params) {
    Map<String, Object> result = new HashMap<>();
    try {
      String studentId = (String) params.get("studentId");
      String email = (String) params.get("email");

      User student = userService.getUserById(studentId);
      if (student == null) {
        result.put("success", false);
        result.put("message", "学生不存在");
        return result;
      }

      if (email == null || email.isEmpty()) {
        email = student.getEmail();
      }

      if (email == null || email.isEmpty()) {
        result.put("success", false);
        result.put("message", "学生邮箱为空，请手动输入邮箱地址");
        return result;
      }

      Map<String, Object> condition = new HashMap<>();
      condition.put("studentName", studentId);
      condition.put("level", "0");
      List<Course> scores = scoreService.getExportList(condition);

      if (scores == null || scores.isEmpty()) {
        result.put("success", false);
        result.put("message", "没有找到该学生的成绩数据");
        return result;
      }

      mailService.sendScoreEmail(email, student.getRealName(), scores);

      result.put("success", true);
      result.put("message", "成绩单已发送到邮箱: " + email);
    } catch (Exception e) {
      result.put("success", false);
      result.put("message", "发送邮件失败: " + e.getMessage());
    }
    return result;
  }
}
