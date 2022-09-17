package com.group.KGMS.repository;


import com.group.KGMS.entity.T_resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeRepository extends JpaRepository<T_resume,Integer> , JpaSpecificationExecutor<T_resume> {
}
