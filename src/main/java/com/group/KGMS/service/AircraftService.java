package com.group.KGMS.service;



import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_aircraft;

import java.util.List;
public interface AircraftService {

    PageInfo<T_aircraft> findAllAircraft(Integer page, Integer size);

    PageInfo<T_aircraft> search(RuleForm ruleForm);

    int save(T_aircraft aircraft);

    int update(T_aircraft aircraft);

    int delete(int id);

    T_aircraft findById(int id);
}


