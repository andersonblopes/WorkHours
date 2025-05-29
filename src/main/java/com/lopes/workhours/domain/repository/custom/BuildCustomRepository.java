package com.lopes.workhours.domain.repository.custom;

import com.lopes.workhours.domain.entities.Build;
import com.lopes.workhours.domain.filter.BuildFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuildCustomRepository {

    Page<Build> findByFilter(BuildFilter filter, Pageable pageable);

    Long countByFilter(BuildFilter filter);
}
