package com.szh.sms.dao.Upload;

import com.szh.sms.domain.Upload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Description 上传
 **/
@Mapper
public interface UploadMapper {
  /**
   * description: 上传头像
   *
   * @param: condition
   * return: void
   */
  void add(Upload upload);
  /**
  * description: 获取头像
  *
  * @param: condition
  */
  String getHead(@Param("condition") Map<String, Object> condition);
  /**
  * description: 根据用户id和等级查询用户是否存在
  * @param: condition
  * return: Integer
  */
  Integer checkCount(@Param("condition") Map<String, Object> condition);
  /**
  * description: 修改用户头像
  * @param: upload
  * return: void
  */
  void update(Upload upload);
}
