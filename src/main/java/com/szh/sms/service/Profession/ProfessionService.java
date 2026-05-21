package com.szh.sms.service.Profession;

import com.szh.sms.domain.Profession;

import java.util.List;

/**
 * Description 专业信息Service层
 **/
public interface ProfessionService {
/**
* description: 获取专业
* return:
*/
  List<Profession> getProfessionList();

/**
* description: 新增专业
* return:
*/
  void addProfession(Profession profession);

/**
* description: 修改专业
* return:
*/
  void updateProfession(Profession profession);

/**
* description: 删除专业
* return:
*/
  void deleteProfession(Integer id);

/**
* description: 批量删除专业
* return:
*/
  void deleteProfessions(List<Integer> ids);
}
