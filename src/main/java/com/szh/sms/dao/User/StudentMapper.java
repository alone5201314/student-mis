package com.szh.sms.dao.User;

import com.github.pagehelper.PageRowBounds;
import com.szh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 学生用户mapper层
 **/
@Mapper
public interface StudentMapper {
  /**
   * description: 新增学生账号信息
   * @param user
   * @return void
   */
  void addStudent(User user);

  /**
   * description: 删除学生账号
   *
   * @param ids
   * @return void
   */
  void delete(@Param("ids") List<String> ids);

  /**
   * description: 修改学生账号
   *
   * @param user
   * @return void
   */
  void update(User user);

  /**
   * description: 获取学生账号信息列表
   * @param rowBounds
   * @return java.util.List<com.szh.sms.dto.User>
   */
  List<User> getStudentList(PageRowBounds rowBounds,  @Param("condition") Map<String, Object> condition);
  /**
  * description:
  * @param:
  * return:
  */
  List<User> getStudentTree();
  /**
  * description: 根据专业获取该专业下的所有班级
  * @param: String
  * return: List<String>
  */
  List<String> getGradeByProfession(@Param("code") String code);
  /**
   * description: 查看人数
   * return: Integer
   */
  Integer checkCodeCount(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据id获取管理员信息
   * @param:
   * return:
   */
  User getUserById(@Param("id") String id);
  /**
  * description: 根据id获取学生信息
  * @param:
  * return:
  */
  User getStudentById(@Param("id") String id);
}
