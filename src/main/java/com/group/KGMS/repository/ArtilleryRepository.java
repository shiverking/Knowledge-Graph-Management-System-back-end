package com.group.KGMS.repository;

import com.group.KGMS.entity.T_artillery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArtilleryRepository extends JpaRepository<T_artillery,Integer>, JpaSpecificationExecutor<T_artillery> {
}
