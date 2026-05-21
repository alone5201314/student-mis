package com.szh.sms.service.Profession.impl;

import com.szh.sms.dao.Profession.ProfessionMapper;
import com.szh.sms.domain.Profession;
import com.szh.sms.service.Profession.ProfessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 **/
@Service
public class ProfessionServiceImpl implements ProfessionService {
  @Resource
  private ProfessionMapper professionMapper;

  @Override
  public List<Profession> getProfessionList() {
    return professionMapper.getProfessionList();
  }
}
