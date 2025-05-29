package com.lopes.workhours.domain.repository.custom;

import com.lopes.workhours.domain.entities.Owner;
import com.lopes.workhours.domain.filter.OwnerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OwnerCustomRepository {

    Page<Owner> findByFilter(OwnerFilter filter, Pageable pageable);

    Long countByFilter(OwnerFilter filter);
}
