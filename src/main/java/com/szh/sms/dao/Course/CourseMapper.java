package com.szh.sms.dao.Course;

import com.github.pagehelper.PageRowBounds;
import com.szh.sms.dto.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 课程信息mapper层
 **/
@Mapper
public interface CourseMapper {
  /**
   * description: 新增课程信息
   * @param course
   * @return void
   */
  void addCourse(Course course);

  /**
   * description: 删除课程信息
   *
   * @param ids
   * @return void
   */
  void delete(@Param("ids") List<Integer> ids);

  /**
   * description: 修改课程信息
   *
   * @param course
   * @return void
   */
  void update(Course course);
  /**
  * description: 获取课程信息
  * param: rowBounds
  * param: condition
  * return: List<Course>
  */
  List<Course> getCourseList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);
  /**
   * description: 查看课程最大id
   * return: Integer
   */
  String checkCodeCount(@Param("condition") Map<String, Object> condition);
  /**
  * description: 根据专业、学期获取课程列表
  * @param: condition
  * return:  List<Course>
  */
  List<Course> getCourseByMap(@Param("condition") Map<String, Object> condition);

  /**
   * description: 根据课程id获取课程信息
   * @param: String
   * return: Course
   */
  Course getCourseById(@Param("courseId") String courseId);
}
