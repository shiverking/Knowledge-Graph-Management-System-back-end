package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;

import com.group.KGMS.entity.T_plan;
import com.group.KGMS.mapper.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    PlanMapper planMapper;

    @Override
    public PageInfo<T_plan> findAllPlan(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<T_plan> info = new PageInfo<T_plan>(planMapper.findAllPlan());
        return info;
    }

    @Override
    public PageInfo<T_plan> search(RuleForm ruleForm) {
        PageHelper.startPage(ruleForm.getPage(), ruleForm.getSize());
        PageInfo<T_plan> info = new PageInfo<T_plan>(planMapper.search(ruleForm));
        return info;
    }

    @Override
    public int save(T_plan person) {
        return planMapper.save(person);
    }

    @Override
    public int update(T_plan person) {
        return planMapper.update(person);
    }

    @Override
    public int delete(int id) {
        return planMapper.delete(id);
    }

    @Override
    public T_plan findById(int id) {
        return planMapper.findById(id);
    }

    @Override
    public int maxid() {
        return planMapper.maxid();
    }
}
