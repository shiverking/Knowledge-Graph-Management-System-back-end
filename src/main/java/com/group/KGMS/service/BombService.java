package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_bomb;

import java.util.List;

public interface BombService {
    T_bomb findById(int id);

    PageInfo<T_bomb> findAllBomb(Integer page, Integer size);

    PageInfo<T_bomb> search(RuleForm ruleForm);

    int save(T_bomb bomb);

    int update(T_bomb bomb);

    int delete(int id);
}
