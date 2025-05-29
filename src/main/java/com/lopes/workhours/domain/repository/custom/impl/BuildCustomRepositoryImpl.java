package com.lopes.workhours.domain.repository.custom.impl;

import com.lopes.workhours.domain.entities.Build;
import com.lopes.workhours.domain.filter.BuildFilter;
import com.lopes.workhours.domain.repository.custom.BuildCustomRepository;
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

import java.util.NoSuchElementException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
public class BuildCustomRepositoryImpl implements BuildCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Build> findByFilter(final BuildFilter filter, final Pageable pageable) {
        TypedQuery<Build> typedQuery;
        Page<Build> page;

        StringBuilder jpql = new StringBuilder("""
                SELECT
                 DISTINCT e
                FROM
                 Build e
                 INNER JOIN FETCH e.owner owner
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
            jpql.append(" ORDER BY e.updatedAt DESC ");
        }

        try {

            typedQuery = entityManager.createQuery(jpql.toString(), Build.class);

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
    public Long countByFilter(BuildFilter filter) {
        long total = 0L;

        StringBuilder jpql = new StringBuilder("""
                SELECT COUNT(DISTINCT e.id)
                FROM
                 Build e
                 INNER JOIN e.owner owner
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

    private String mountWhere(BuildFilter filter) {
        final StringBuilder where = new StringBuilder(" WHERE 1=1 ");

        if (hasText(filter.getOwner())) {
            where.append("""
                    AND (
                            CAST(UNACCENT(UPPER(owner.name)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :owner, '%'))) AS STRING)
                            OR
                            CAST(UNACCENT(UPPER(owner.nickName)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :owner, '%'))) AS STRING)
                        )
                    """);
        }

        if (hasText(filter.getDescription())) {
            where.append("""
                    AND CAST(UNACCENT(UPPER(e.description)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :description, '%'))) AS STRING)
                    """);
        }

        if (hasText(filter.getAddress())) {
            where.append("""
                    AND CAST(UNACCENT(UPPER(e.address)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :address, '%'))) AS STRING)
                    """);
        }

        if (filter.getActive() != null) {
            where.append("""
                    AND e.active = :active
                    """);
        }

        return where.toString();
    }
    
    private void setParameters(BuildFilter filter, Query query) {

        if (hasText(filter.getOwner())) {
            query.setParameter("owner", filter.getOwner());
        }

        if (hasText(filter.getDescription())) {
            query.setParameter("description", filter.getDescription());
        }

        if (hasText(filter.getAddress())) {
            query.setParameter("address", filter.getAddress());
        }

        if (filter.getActive() != null) {
            query.setParameter("active", filter.getActive());
        }
    }
}
