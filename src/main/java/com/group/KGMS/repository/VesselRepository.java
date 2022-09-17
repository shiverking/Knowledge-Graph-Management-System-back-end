package com.group.KGMS.repository;

import com.group.KGMS.entity.T_vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VesselRepository extends JpaRepository<T_vessel,Integer> , JpaSpecificationExecutor<T_vessel> {
}
