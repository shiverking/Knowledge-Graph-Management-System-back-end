package com.group.KGMS.repository;

import com.group.KGMS.entity.T_bomb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BombRepository extends JpaRepository<T_bomb,Integer>, JpaSpecificationExecutor<T_bomb> {
}
