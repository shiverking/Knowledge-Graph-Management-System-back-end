package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_aircraft;
import com.group.KGMS.mapper.AircraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    AircraftMapper aircraftMapper;

    @Override
    public PageInfo<T_aircraft> findAllAircraft(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<T_aircraft> info = new PageInfo<T_aircraft>(aircraftMapper.findAllAircraft());
        return info;
    }

    @Override
    public PageInfo<T_aircraft> search(RuleForm ruleForm) {
        PageHelper.startPage(ruleForm.getPage(), ruleForm.getSize());
        PageInfo<T_aircraft> info = new PageInfo<T_aircraft>(aircraftMapper.search(ruleForm));
        return info;
    }

    @Override
    public int save(T_aircraft aircraft) {
        return aircraftMapper.save(aircraft);
    }

    @Override
    public int update(T_aircraft aircraft) {
        return aircraftMapper.update(aircraft);
    }

    @Override
    public int delete(int id) {
        return aircraftMapper.delete(id);
    }

    @Override
    public T_aircraft findById(int id) {
        return aircraftMapper.findById(id);
    }

}
