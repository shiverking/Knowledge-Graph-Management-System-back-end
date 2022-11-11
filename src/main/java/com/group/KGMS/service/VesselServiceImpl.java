package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_person;
import com.group.KGMS.entity.T_vessel;
import com.group.KGMS.mapper.VesselMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VesselServiceImpl implements VesselService{
    @Autowired
    VesselMapper vesselMapper;
    @Override
    public List<T_vessel> findAll(){
        return vesselMapper.findAllVessel();
    }
    @Override
    public PageInfo<T_vessel> findAllVessel(Integer page, Integer size){
        PageHelper.startPage(page,size);
        PageInfo<T_vessel> info = new PageInfo<T_vessel>(vesselMapper.findAllVessel());
        return info;
    };

    @Override
    public PageInfo<T_vessel> search(RuleForm ruleForm){
        PageHelper.startPage(ruleForm.getPage(),ruleForm.getSize());
        PageInfo<T_vessel> info = new PageInfo<T_vessel>(vesselMapper.search(ruleForm));
        return info;
    };

    @Override
    public int save(T_vessel vessel){
        return vesselMapper.save(vessel);
    };
    @Override
    public int update(T_vessel vessel){
        return vesselMapper.update(vessel);
    };
    @Override
    public int delete(int id){
        return vesselMapper.delete(id);
    };
    @Override
    public T_vessel findById(int id){
        return vesselMapper.findById(id);
    };
    @Override
    public List<T_vessel> findByTaskid(int task_id){
        return vesselMapper.findByTaskid(task_id);
    }
    @Override
    public int deleteTaskid(int task_id){
        return vesselMapper.deleteTaskid(task_id);
    }
    @Override
    public int saveTaskid( int task_id,  int id){
        return vesselMapper.saveTaskid(task_id,id);
    }
}
