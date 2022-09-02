package com.group.KGMS.repository;

import com.group.KGMS.entity.T_task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<T_task,Integer> {
}
