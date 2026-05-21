package com.szh.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 树形实体类
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
  private String name;
  private String [] school;
  private String [] profession;
}
