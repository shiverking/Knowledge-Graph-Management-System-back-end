package com.group.KGMS.repository;

import com.group.KGMS.entity.T_misile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MisileRepository extends JpaRepository<T_misile,Integer> ,JpaSpecificationExecutor<T_misile> {
}
