package com.szh.sms.service.Profession.impl;

import com.szh.sms.dao.Profession.ProfessionMapper;
import com.szh.sms.domain.Profession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessionServiceImplTest {

  @Mock
  private ProfessionMapper professionMapper;

  @InjectMocks
  private ProfessionServiceImpl professionService;

  @Test
  void getProfessionListReturnsCourseCount() {
    Profession profession = profession(1, "计算机");
    profession.setCourseCount(3);
    when(professionMapper.getProfessionList()).thenReturn(Collections.singletonList(profession));

    List<Profession> professions = professionService.getProfessionList();

    assertEquals(3, professions.get(0).getCourseCount());
  }

  @Test
  void addProfessionTrimsAndCreatesUniqueName() {
    Profession profession = profession(null, " 新专业 ");
    when(professionMapper.checkProfessionCount("新专业")).thenReturn(0);

    professionService.addProfession(profession);

    assertEquals("新专业", profession.getName());
    verify(professionMapper).addProfession(profession);
  }

  @Test
  void addProfessionRejectsDuplicateName() {
    Profession profession = profession(null, "计算机");
    when(professionMapper.checkProfessionCount("计算机")).thenReturn(1);

    ResponseStatusException exception = assertThrows(
        ResponseStatusException.class,
        () -> professionService.addProfession(profession));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    verify(professionMapper, never()).addProfession(any(Profession.class));
  }

  @Test
  void updateProfessionRenamesProfessionAndRelatedData() {
    Profession oldProfession = profession(1, "旧专业");
    Profession newProfession = profession(1, "新专业");
    when(professionMapper.getProfessionById(1)).thenReturn(oldProfession);
    when(professionMapper.checkProfessionCount("新专业")).thenReturn(0);

    professionService.updateProfession(newProfession);

    verify(professionMapper).updateProfession(newProfession);
    verify(professionMapper).updateStudentProfession("旧专业", "新专业");
    verify(professionMapper).updateCourseProfession("旧专业", "新专业");
    verify(professionMapper).updateTeacherCourseProfession("旧专业", "新专业");
    verify(professionMapper).updateTimetableProfession("旧专业", "新专业");
    verify(professionMapper).updateCourseInfoProfession("旧专业", "新专业");
  }

  @Test
  void deleteProfessionDeletesUnreferencedProfession() {
    Profession profession = profession(1, "未使用专业");
    when(professionMapper.getProfessionById(1)).thenReturn(profession);
    when(professionMapper.countProfessionReferences("未使用专业")).thenReturn(0);

    professionService.deleteProfession(1);

    verify(professionMapper).deleteProfession(1);
  }

  @Test
  void deleteProfessionsRejectsBatchWhenAnyProfessionIsReferenced() {
    Profession unused = profession(1, "未使用专业");
    Profession used = profession(2, "已使用专业");
    when(professionMapper.getProfessionById(1)).thenReturn(unused);
    when(professionMapper.getProfessionById(2)).thenReturn(used);
    when(professionMapper.countProfessionReferences("未使用专业")).thenReturn(0);
    when(professionMapper.countProfessionReferences("已使用专业")).thenReturn(1);

    ResponseStatusException exception = assertThrows(
        ResponseStatusException.class,
        () -> professionService.deleteProfessions(Arrays.asList(1, 2)));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    verify(professionMapper, never()).deleteProfession(1);
    verify(professionMapper, never()).deleteProfession(2);
  }

  @Test
  void deleteProfessionRejectsReferencedProfession() {
    Profession profession = profession(1, "电子信息工程");
    when(professionMapper.getProfessionById(1)).thenReturn(profession);
    when(professionMapper.countProfessionReferences("电子信息工程")).thenReturn(3);

    ResponseStatusException exception = assertThrows(
        ResponseStatusException.class,
        () -> professionService.deleteProfession(1));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    verify(professionMapper, never()).deleteProfession(1);
  }

  private Profession profession(Integer id, String name) {
    Profession profession = new Profession();
    profession.setId(id);
    profession.setName(name);
    return profession;
  }
}
