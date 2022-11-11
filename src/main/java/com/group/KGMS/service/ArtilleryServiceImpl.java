package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_artillery;
import com.group.KGMS.mapper.ArtilleryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtilleryServiceImpl implements ArtilleryService{
    @Autowired
    ArtilleryMapper artilleryMapper;

    @Override
    public PageInfo<T_artillery> findAllArtillery(Integer page, Integer size){
        PageHelper.startPage(page,size);
        PageInfo<T_artillery> info = new PageInfo<T_artillery>(artilleryMapper.findAllArtillery());
        return info;
    };

    @Override
    public PageInfo<T_artillery> search(RuleForm ruleForm){
        PageHelper.startPage(ruleForm.getPage(),ruleForm.getSize());
        PageInfo<T_artillery> info = new PageInfo<T_artillery>(artilleryMapper.search(ruleForm));
        return info;
    };

    @Override
    public int save(T_artillery artillery){
        return artilleryMapper.save(artillery);
    };
    @Override
    public int update(T_artillery artillery){
        return artilleryMapper.update(artillery);
    };
    @Override
    public int delete(int id){
        return artilleryMapper.delete(id);
    };
    @Override
    public T_artillery findById(int id){
        return artilleryMapper.findById(id);
    };
}
