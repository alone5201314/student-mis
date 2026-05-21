package com.szh.sms.dao.User;

import com.github.pagehelper.PageRowBounds;
import com.szh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 教师用户mapper层
 **/
@Mapper
public interface TeacherMapper {

  /**
   * description: 新增教师账号信息
   * @param user
   * @return void
   */
  void addTeacher(User user);

  /**
   * description: 删除教师账号
   *
   * @param ids
   * @return void
   */
  void delete(@Param("ids") List<Integer> ids);

  /**
   * description: 修改教师账号
   *
   * @param user
   * @return void
   */
  void update(User user);

  /**
   * description: 获取教师账号信息列表
   * @param rowBounds
   * @return java.util.List<com.szh.sms.dto.User>
   */
  List<User> getTeacherList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);
  /**
   * description:
   * @param:
   * return:
   */
  User getUserById(@Param("id") String id);
  /**
   * description: 查看人数
   * return: Integer
   */
  Integer checkCodeCount();
}
