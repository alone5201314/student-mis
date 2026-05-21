package com.szh.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 专业实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profession {
  /**
   * 专业id
   */
  private Integer id;
  /**
   * 专业名
   */
  private String name;
  /**
   * 关联课程数量
   */
  private Integer courseCount;
}
