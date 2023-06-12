package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;

import com.group.KGMS.entity.T_education;
import com.group.KGMS.entity.T_person;
import com.group.KGMS.entity.T_resume;
import com.group.KGMS.mapper.PersonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonMapper personMapper;

    @Override
    public List<T_person> findAll() {
        return personMapper.findAllPerson();
    }

    @Override
    public PageInfo<T_person> findAllPerson(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<T_person> info = new PageInfo<T_person>(personMapper.findAllPerson());
        return info;
    }

    @Override
    public PageInfo<T_person> search(RuleForm ruleForm) {
        PageHelper.startPage(ruleForm.getPage(), ruleForm.getSize());
        PageInfo<T_person> info = new PageInfo<T_person>(personMapper.search(ruleForm));
        return info;
    }

    @Override
    public int save(T_person person) {
        return personMapper.save(person);
    }

    @Override
    public int update(T_person person) {
        return personMapper.update(person);
    }

    @Override
    public int updateplan(int plan_id, int id) {
        return personMapper.updateplan(plan_id, id);
    }

    @Override
    public int delete(int id) {
        return personMapper.delete(id);
    }

    @Override
    public T_person findById(int id) {
        return personMapper.findById(id);
    }

    @Override
    public int maxid() {
        return personMapper.maxid();
    }

    @Override
    public int saveedu(T_education education) {
        return personMapper.saveedu(education);
    }

    @Override
    public int saveresume(T_resume resume) {
        return personMapper.saveresume(resume);
    }

    @Override
    public List<T_education> searchedu(int person_id) {

        return personMapper.searchedu(person_id);
    }

    @Override
    public List<T_resume> searchresu(int person_id) {

        return personMapper.searchresu(person_id);
    }

    @Override
    public int deleteedu(int person_id) {
        return personMapper.deleteedu(person_id);
    }

    @Override
    public int deleteresu(int person_id) {
        return personMapper.deleteresu(person_id);
    }

    @Override
    public List<T_person> findByPlanid(int plan_id) {
        return personMapper.findByPlanid(plan_id);
    }

    @Override
    public List<T_person> findByTaskid(int task_id) {
        return personMapper.findByTaskid(task_id);
    }

    @Override
    public int deleteTaskid(int task_id) {
        return personMapper.deleteTaskid(task_id);
    }

    @Override
    public int saveTaskid(int task_id,int plan_id, int id) {
        return personMapper.saveTaskid(task_id,plan_id, id);
    }
}
