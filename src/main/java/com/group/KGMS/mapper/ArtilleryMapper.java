package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_artillery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArtilleryMapper {

    T_artillery findById(int id);

    List<T_artillery> findAllArtillery();

    List<T_artillery> search(RuleForm ruleForm);

    int save(T_artillery artillery);

    int update(T_artillery artillery);

    int delete(int id);
}
