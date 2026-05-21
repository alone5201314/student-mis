package com.szh.sms.service.Profession.impl;

import com.szh.sms.dao.Profession.ProfessionMapper;
import com.szh.sms.domain.Profession;
import com.szh.sms.service.Profession.ProfessionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

  @Override
  public void addProfession(Profession profession) {
    String name = normalizeName(profession.getName());
    ensureUniqueName(name, null);
    profession.setName(name);
    professionMapper.addProfession(profession);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateProfession(Profession profession) {
    if (profession.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "专业ID不能为空");
    }
    String newName = normalizeName(profession.getName());
    Profession oldProfession = getExistingProfession(profession.getId());
    if (oldProfession.getName().equals(newName)) {
      return;
    }
    ensureUniqueName(newName, profession.getId());

    profession.setName(newName);
    professionMapper.updateProfession(profession);
    professionMapper.updateStudentProfession(oldProfession.getName(), newName);
    professionMapper.updateCourseProfession(oldProfession.getName(), newName);
    professionMapper.updateTeacherCourseProfession(oldProfession.getName(), newName);
    professionMapper.updateTimetableProfession(oldProfession.getName(), newName);
    professionMapper.updateCourseInfoProfession(oldProfession.getName(), newName);
  }

  @Override
  public void deleteProfession(Integer id) {
    if (id == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "专业ID不能为空");
    }
    deleteProfessions(java.util.Collections.singletonList(id));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteProfessions(List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "请选择要删除的专业");
    }
    for (Integer id : ids) {
      if (id == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "专业ID不能为空");
      }
      Profession profession = getExistingProfession(id);
      Integer references = professionMapper.countProfessionReferences(profession.getName());
      if (references != null && references > 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "该专业已有业务数据，不能删除");
      }
    }
    for (Integer id : ids) {
      professionMapper.deleteProfession(id);
    }
  }

  private Profession getExistingProfession(Integer id) {
    Profession profession = professionMapper.getProfessionById(id);
    if (profession == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "专业不存在");
    }
    return profession;
  }

  private void ensureUniqueName(String name, Integer currentId) {
    Integer count = professionMapper.checkProfessionCount(name);
    if (count == null || count == 0) {
      return;
    }
    if (currentId != null) {
      Profession profession = professionMapper.getProfessionById(currentId);
      if (profession != null && profession.getName().equals(name)) {
        return;
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "专业名称已存在");
  }

  private String normalizeName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "专业名称不能为空");
    }
    return name.trim();
  }
}
