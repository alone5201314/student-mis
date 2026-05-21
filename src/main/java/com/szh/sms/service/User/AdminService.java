package com.szh.sms.service.User;

import com.szh.sms.dto.User;
import com.szh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Description 管理员Service层
 **/
public interface AdminService {
  /**
   * description: 新增学生账号
   * @param user
   * @return void
   */
  void add(User user);

  /**
   * description: 删除学生账号
   *
   * @param ids
   * @return void
   */
  void delete(List<Integer> ids);

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
  PagingResult<User> getAdminList(RowBounds rowBounds, Map<String, Object> condition);
}
