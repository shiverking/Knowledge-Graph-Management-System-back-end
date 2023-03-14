package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_misile;
import com.group.KGMS.mapper.MisileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MisileServiceImpl implements MisileService {
    @Autowired
    MisileMapper misileMapper;

    @Override
    public PageInfo<T_misile> findAllMisile(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<T_misile> info = new PageInfo<T_misile>(misileMapper.findAllMisile());
        return info;
    }

    @Override
    public PageInfo<T_misile> search(RuleForm ruleForm) {
        PageHelper.startPage(ruleForm.getPage(), ruleForm.getSize());
        PageInfo<T_misile> info = new PageInfo<T_misile>(misileMapper.search(ruleForm));
        return info;
    }

    @Override
    public int save(T_misile misile) {
        return misileMapper.save(misile);
    }

    @Override
    public int update(T_misile misile) {
        return misileMapper.update(misile);
    }

    @Override
    public int delete(int id) {
        return misileMapper.delete(id);
    }

    @Override
    public T_misile findById(int id) {
        return misileMapper.findById(id);
    }

}
