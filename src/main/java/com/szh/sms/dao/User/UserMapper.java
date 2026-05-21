package com.szh.sms.dao.User;

import com.szh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Description 登陆用户mapper层
 **/
@Mapper
public interface UserMapper {
  /**
   * description: 根据学生用户、密码登陆
   * @params: condition
   * @return: com.szh.sms.dto.User
   */
  User getStudentInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据老师用户、密码登陆
   * @params: condition
   * @return: com.szh.sms.dto.User
   */
  User getTeacherInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据管理员用户、密码登陆
   * @params: condition
   * @return: com.szh.sms.dto.User
   */
  User getAdminInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 修改密码
   * @params: condition
   * @return: void
   */
  void update(@Param("condition") Map<String, Object> condition);
  /**
   * description: 查询是否与原密码相同
   * @params: condition
   * @return: Integer
   */
  Integer checkPasswordCount(@Param("condition") Map<String, Object> condition);
  /**
  * description: 获取默哀模式
  * return: Integer
  */
  Integer getSilent();
  /**
   * description: 获取默哀模式
   * return: Integer
   */
  void setSilent(@Param("state") Integer state);
}
