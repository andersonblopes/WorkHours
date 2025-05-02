package com.lopes.workhours.domain.repository.custom.impl;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.domain.repository.custom.WorkLogCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
public class WorkLogCustomRepositoryImpl implements WorkLogCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<WorkLog> findByFilter(final WorkLogFilter filter, final Pageable pageable) {
        TypedQuery<WorkLog> typedQuery;
        Page<WorkLog> page;

        StringBuilder jpql = new StringBuilder("""
                SELECT
                 DISTINCT e
                FROM
                 WorkLog e
                 INNER JOIN FETCH e.employee employee
                 INNER JOIN FETCH e.apartment apartment
                 INNER JOIN FETCH apartment.build build
                 INNER JOIN FETCH e.user user
                """);

        jpql.append(mountWhere(filter));

        try {

            Sort.Order order = pageable.getSort().iterator().next();

            jpql.append(" ORDER BY e.");
            jpql.append(order.getProperty());
            jpql.append(" ");
            jpql.append(order.getDirection());

        } catch (NoSuchElementException e) {
            log.warn("Set order query exception: {}", e.getMessage());
            jpql.append(" ORDER BY e.executionDate DESC ");
        }

        try {

            typedQuery = entityManager.createQuery(jpql.toString(), WorkLog.class);

            setParameters(filter, typedQuery);

            if (pageable.isPaged()) {
                typedQuery.setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())));
                typedQuery.setMaxResults(pageable.getPageSize());
            }

            page = new PageImpl<>(typedQuery.getResultList(), pageable, countByFilter(filter));

        } catch (Exception e) {
            log.error("Query execution exception: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        return page;
    }

    @Override
    public Long countByFilter(WorkLogFilter filter) {
        long total = 0L;

        StringBuilder jpql = new StringBuilder("""
                SELECT COUNT(DISTINCT e.id)
                FROM
                 WorkLog e
                 INNER JOIN e.employee employee
                 INNER JOIN e.apartment apartment
                 INNER JOIN apartment.build build
                 INNER JOIN e.user user
                """);

        jpql.append(mountWhere(filter));

        try {
            final var query = entityManager.createQuery(jpql.toString());
            setParameters(filter, query);

            total = Long.parseLong(query.getSingleResult().toString());

        } catch (Exception e) {
            log.error("Query execution exception for countByFilter: {}", e.getMessage());
        }

        return total;
    }

    private String mountWhere(WorkLogFilter filter) {
        final StringBuilder where = new StringBuilder(" WHERE 1=1 ");

        if (hasText(filter.getEmployee())) {
            where.append("""
                    AND (
                        CAST(UNACCENT(UPPER(employee.nickName)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :employeeDesc, '%'))) AS STRING)
                        OR
                        CAST(UNACCENT(UPPER(employee.name)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :employeeDesc, '%'))) AS STRING)
                    )
                    """);
        }

        if (hasText(filter.getDescription())) {
            where.append("""
                    AND (
                            CAST(UNACCENT(UPPER(build.description)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :description, '%'))) AS STRING)
                            OR
                            CAST(UNACCENT(UPPER(apartment.description)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :description, '%'))) AS STRING)
                        )
                    """);
        }

        if (nonNull(filter.getStartDate()) && nonNull(filter.getEndDate())) {
            where.append(" AND e.executionDate BETWEEN :startDate AND :endDate ");
        } else if (nonNull(filter.getStartDate())) {
            where.append(" AND e.executionDate >= :startDate ");
        } else if (nonNull(filter.getEndDate())) {
            where.append(" AND e.executionDate <= :endDate ");
        }

        return where.toString();
    }


    private void setParameters(WorkLogFilter filter, Query query) {

        if (nonNull(filter.getStartDate())) {
            query.setParameter("startDate", LocalDateTime.parse(filter.getStartDate() + "T00:00:00"));
        }

        if (nonNull(filter.getEndDate())) {
            query.setParameter("endDate", LocalDateTime.parse(filter.getEndDate() + "T23:59:59"));
        }

        if (hasText(filter.getEmployee())) {
            query.setParameter("employeeDesc", filter.getEmployee());
        }

        if (hasText(filter.getDescription())) {
            query.setParameter("description", filter.getDescription());
        }
    }
}
