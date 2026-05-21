package com.szh.sms.service.User;

import com.szh.sms.dto.User;
import com.szh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Description 学生账号接口
 **/
public interface StudentService {
  /**
  * description: 新增学生账号
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
  void delete(List<String> ids);

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
   * @param condition
   * @return com.szh.sms.utils.PagingResult
   */
  PagingResult<User> getStudentList(RowBounds rowBounds, Map<String, Object> condition);
}
