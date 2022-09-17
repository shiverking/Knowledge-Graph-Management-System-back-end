package com.group.KGMS.repository;

import com.group.KGMS.entity.T_plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PlanRepository extends JpaRepository<T_plan,Integer> , JpaSpecificationExecutor<T_plan> {
    @Query("select max(id) from T_plan where 1 = 1")
    int max();
}
