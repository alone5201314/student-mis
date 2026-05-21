package com.szh.sms.service.User;

import com.szh.sms.dto.User;

import java.util.List;
import java.util.Map;

/**
 * Description 登陆用户接口
 **/
public interface UserService {
  /**
  * description: 根据用户、密码登陆
  * @params: condition
  * @return: com.szh.sms.dto.User
  */
  User getStudentInfo(Map<String, Object> condition);
  /**
   * description: 修改密码
   * @param condition
   * @return void
   */
  boolean update(Map<String, Object> condition);
  /**
  * description: 获取树状数据
  * return: List<Object>
  */
  List<Object> getTree();
  /**
  * description: 获取token
  * @param: User
  * return: String
  */
  String getToken(User user, long time);
  /**
  * description: 根据等级和id获取用户信息
  * @param: condition
  * return: User
  */
  User findUser(Map<String, Object> condition);
  /**
  * description: 获取默哀模式
  * return: boolean
  */
  boolean getSilent();
  /**
  * description: 修改默哀模式
  * @param: String
  * return: boolean
  */
  boolean setSilent(Integer state);

  User getUserById(String id);
}
