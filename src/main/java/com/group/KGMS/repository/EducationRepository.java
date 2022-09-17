package com.group.KGMS.repository;


import com.group.KGMS.entity.T_education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EducationRepository extends JpaRepository<T_education,Integer> , JpaSpecificationExecutor<T_education> {
    @Query("select max(person_id) from T_education where 1 = 1")
    int max();


}
