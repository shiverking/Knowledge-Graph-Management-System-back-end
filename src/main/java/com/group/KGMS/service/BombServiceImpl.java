package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;

import com.group.KGMS.entity.T_bomb;

import com.group.KGMS.mapper.BombMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BombServiceImpl implements BombService {
    @Autowired
    BombMapper bombMapper;

    @Override
    public PageInfo<T_bomb> findAllBomb(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<T_bomb> info = new PageInfo<T_bomb>(bombMapper.findAllBomb());
        return info;
    }

    @Override
    public PageInfo<T_bomb> search(RuleForm ruleForm) {
        PageHelper.startPage(ruleForm.getPage(), ruleForm.getSize());
        PageInfo<T_bomb> info = new PageInfo<T_bomb>(bombMapper.search(ruleForm));
        return info;
    }

    @Override
    public int save(T_bomb artillery) {
        return bombMapper.save(artillery);
    }

    @Override
    public int update(T_bomb artillery) {
        return bombMapper.update(artillery);
    }

    @Override
    public int delete(int id) {
        return bombMapper.delete(id);
    }

    @Override
    public T_bomb findById(int id) {
        return bombMapper.findById(id);
    }

}
