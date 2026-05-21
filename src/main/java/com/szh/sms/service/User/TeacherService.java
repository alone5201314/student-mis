package com.szh.sms.service.User;

import com.szh.sms.dto.User;
import com.szh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Description 教师用户接口
 **/
public interface TeacherService {
  /**
   * description: 新增教师账号
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
  void delete(List<Integer> ids);

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
   * @param condition
   * @return com.szh.sms.utils.PagingResult
   */
  PagingResult<User> getTeacherList(RowBounds rowBounds, Map<String, Object> condition);
}
