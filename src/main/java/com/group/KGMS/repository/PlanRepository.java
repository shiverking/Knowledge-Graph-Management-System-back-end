package com.group.KGMS.repository;


import com.group.KGMS.entity.T_plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<T_plan,Integer> {
}
