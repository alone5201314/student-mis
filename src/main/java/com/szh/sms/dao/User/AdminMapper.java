package com.szh.sms.dao.User;

import com.github.pagehelper.PageRowBounds;
import com.szh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 管理员账号Mapper层
 **/
@Mapper
public interface AdminMapper {
  /**
   * description: 新增管理员账号信息
   * @param user
   * @return void
   */
  void add(User user);

  /**
   * description: 删除管理员账号
   *
   * @param ids
   * @return void
   */
  void delete(@Param("ids") List<Integer> ids);

  /**
   * description: 修改管理员账号
   *
   * @param user
   * @return void
   */
  void update(User user);

  /**
   * description: 获取管理员账号信息列表
   * @param rowBounds
   * @return java.util.List<com.szh.sms.dto.User>
   */
  List<User> getAdminList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);
  /**
  * description: 根据管理员id获取管理员信息
  * @param: String
  * return: User
  */
  User getUserById(@Param("id") String id);
  /**
   * description: 查看管理员人数
   * return: Integer
   */
  Integer checkCodeCount();
}
