package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_bomb;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BombMapper {

    T_bomb findById(int id);
    List<T_bomb> findAllBomb();
    List<T_bomb> search(RuleForm ruleForm);
    int save(T_bomb bomb);
    int update(T_bomb bomb);
    int delete(int id);
}
