package com.group.KGMS.repository;


import com.group.KGMS.entity.T_taskperson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskpersonRepository extends JpaRepository<T_taskperson,Integer> {
}
