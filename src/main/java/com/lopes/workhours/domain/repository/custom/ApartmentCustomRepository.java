package com.lopes.workhours.domain.repository.custom;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.domain.filter.ApartmentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApartmentCustomRepository {

    Page<Apartment> findByFilter(ApartmentFilter filter, Pageable pageable);

    Long countByFilter(ApartmentFilter filter);
}
