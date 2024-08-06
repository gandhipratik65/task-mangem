package com.banq.assign.task.repos.audit;

import com.banq.assign.task.entity.audit.TaskAud;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskAuditRepository extends JpaRepository<TaskAud, Long>, JpaSpecificationExecutor<TaskAud> {

    List<TaskAud> findById(Long recordId, Sort sort);

}
