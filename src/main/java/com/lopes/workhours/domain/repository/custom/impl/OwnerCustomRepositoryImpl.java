package com.lopes.workhours.domain.repository.custom.impl;

import com.lopes.workhours.domain.entities.Owner;
import com.lopes.workhours.domain.filter.OwnerFilter;
import com.lopes.workhours.domain.repository.custom.OwnerCustomRepository;
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
public class OwnerCustomRepositoryImpl implements OwnerCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Owner> findByFilter(final OwnerFilter filter, final Pageable pageable) {
        TypedQuery<Owner> typedQuery;
        Page<Owner> page;

        StringBuilder jpql = new StringBuilder("""
                SELECT
                 DISTINCT e
                FROM
                 Owner e
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
            jpql.append(" ORDER BY e.updatedAt DESC ");
        }

        try {

            typedQuery = entityManager.createQuery(jpql.toString(), Owner.class);

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
    public Long countByFilter(OwnerFilter filter) {
        long total = 0L;

        StringBuilder jpql = new StringBuilder("""
                SELECT COUNT(DISTINCT e.id)
                FROM
                 Owner e
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

    private String mountWhere(OwnerFilter filter) {
        final StringBuilder where = new StringBuilder(" WHERE 1=1 ");

        if (hasText(filter.getUserNickName())) {
            where.append("""
                    AND CAST(UNACCENT(UPPER(user.nickName)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :userNickName, '%'))) AS STRING)
                    """);
        }

        if (hasText(filter.getName())) {
            where.append("""
                    AND (
                            CAST(UNACCENT(UPPER(e.name)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :name, '%'))) AS STRING)
                            OR
                            CAST(UNACCENT(UPPER(e.nickName)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :name, '%'))) AS STRING)
                        )
                    """);
        }

        if (hasText(filter.getEmail())) {
            where.append("""
                    AND CAST(UNACCENT(UPPER(e.email)) AS STRING) LIKE CAST(UNACCENT(UPPER(CONCAT('%', :email, '%'))) AS STRING)
                    """);
        }

        if (hasText(filter.getPhone())) {
            where.append("""
                    AND e.phoneNumber = :phone
                    """);
        }

        if (filter.getActive() != null) {
            where.append("""
                    AND e.active = :active
                    """);
        }

        return where.toString();
    }


    private void setParameters(OwnerFilter filter, Query query) {

        if (hasText(filter.getUserNickName())) {
            query.setParameter("userNickName", filter.getUserNickName());
        }

        if (hasText(filter.getName())) {
            query.setParameter("name", filter.getName());
        }

        if (hasText(filter.getEmail())) {
            query.setParameter("email", filter.getEmail());
        }

        if (hasText(filter.getPhone())) {
            query.setParameter("phone", filter.getPhone());
        }

        if (filter.getActive() != null) {
            query.setParameter("active", filter.getActive());
        }
    }
}
