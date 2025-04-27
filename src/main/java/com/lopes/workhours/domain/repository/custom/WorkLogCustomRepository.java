package com.lopes.workhours.domain.repository.custom;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkLogCustomRepository {

    Page<WorkLog> findByFilter(WorkLogFilter filter, Pageable pageable);

    Long countByFilter(WorkLogFilter filter);
}
