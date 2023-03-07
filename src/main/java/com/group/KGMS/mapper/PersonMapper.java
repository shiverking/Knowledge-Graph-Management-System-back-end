package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_education;
import com.group.KGMS.entity.T_person;
import com.group.KGMS.entity.T_resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonMapper {

    T_person findById(int id);

    List<T_person> findByPlanid(int plan_id);

    List<T_person> findByTaskid(int task_id);

    List<T_person> findAllPerson();

    List<T_person> search(RuleForm ruleForm);

    int save(T_person person);

    int update(T_person person);

    int delete(int id);

    int maxid();

    int saveedu(T_education education);

    int saveresume(T_resume resume);

    List<T_education> searchedu(int person_id);

    List<T_resume> searchresu(int person_id);

    int deleteedu(int person_id);

    int deleteresu(int person_id);

    int deleteTaskid(int task_id);

    int updateplan(@Param("plan_id") int plan_id, @Param("id") int id);

    int saveTaskid(@Param("task_id") int task_id, @Param("id") int id);
}
