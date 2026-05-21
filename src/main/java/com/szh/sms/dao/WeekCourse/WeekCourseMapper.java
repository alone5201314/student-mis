package com.szh.sms.dao.WeekCourse;

import com.szh.sms.domain.WeekCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 课程表具体安排Mapper层
 **/
@Mapper
public interface WeekCourseMapper {
  /**
   * description: 新增week表数据
   * @param weekCourse
   * @return void
   */
  void add(WeekCourse weekCourse);
  /**
  * description: 修改week表
  * @param: WeekCourse
  * return: void
  */
  void update(WeekCourse weekCourse);
  /**
  * description: 根据专业班级获取课程表信息
  * @param: condition
  * return: List<WeekCourse>
  */
  List<WeekCourse> getWeekCourse(@Param("condition") Map<String, Object> condition);
}
