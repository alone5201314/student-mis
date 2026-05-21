package com.szh.sms.controller.Profession;

import com.szh.sms.domain.Profession;
import com.szh.sms.service.Profession.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Description 所有专业控制层
 **/
@RestController
@RequestMapping("/api/sms/profession")
public class ProfessionController {

  @Autowired
  private ProfessionService professionService;

  @GetMapping("/getProfessionList")
  private List<Profession> getProfessionList () {
    return professionService.getProfessionList();
  }

  @PostMapping
  public void addProfession(@RequestBody Profession profession) {
    professionService.addProfession(profession);
  }

  @PutMapping
  public void updateProfession(@RequestBody Profession profession) {
    professionService.updateProfession(profession);
  }

  @DeleteMapping("/{ids}")
  public void deleteProfession(@PathVariable("ids") Integer[] ids) {
    professionService.deleteProfessions(Arrays.asList(ids));
  }
}
