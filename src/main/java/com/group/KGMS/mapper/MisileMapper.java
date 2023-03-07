package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_misile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MisileMapper {
    T_misile findById(int id);

    List<T_misile> findAllMisile();

    List<T_misile> search(RuleForm ruleForm);

    int save(T_misile misile);

    int update(T_misile misile);

    int delete(int id);
}
