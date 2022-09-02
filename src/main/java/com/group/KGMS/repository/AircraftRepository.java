package com.group.KGMS.repository;

import com.group.KGMS.entity.T_aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AircraftRepository extends JpaRepository<T_aircraft,Integer>, JpaSpecificationExecutor<T_aircraft> {
}
