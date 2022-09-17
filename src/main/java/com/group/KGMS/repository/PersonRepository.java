package com.group.KGMS.repository;


import com.group.KGMS.entity.T_person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<T_person,Integer> , JpaSpecificationExecutor<T_person> {
    @Query("select max(id) from T_person where 1 = 1")
    int max();
}
