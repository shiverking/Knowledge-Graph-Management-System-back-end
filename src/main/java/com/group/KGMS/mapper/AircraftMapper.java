package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_aircraft;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AircraftMapper {
    T_aircraft findById(int id);

    List<T_aircraft> findAllAircraft();

    List<T_aircraft> search(RuleForm ruleForm);

    int save(T_aircraft aircraft);

    int update(T_aircraft aircraft);

    int delete(int id);
}
