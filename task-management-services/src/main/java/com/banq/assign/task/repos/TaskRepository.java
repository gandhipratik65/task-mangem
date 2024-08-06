package com.banq.assign.task.repos;

import com.banq.assign.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Task entities.
 * <p>
 * This interface extends JpaRepository and provides additional query methods
 * for tasks, including support for specifications and pagination.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves a paginated list of tasks based on the given specification.
     *
     * @param specification the specification used to filter tasks
     * @param pageable      the pagination information
     * @return a page of tasks that match the specification
     */
    Page<Task> findAll(Specification<Task> specification, Pageable pageable);

}
