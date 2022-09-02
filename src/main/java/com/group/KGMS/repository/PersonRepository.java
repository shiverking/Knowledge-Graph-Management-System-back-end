package com.group.KGMS.repository;

import com.group.KGMS.entity.T_person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<T_person,Integer> {
}
