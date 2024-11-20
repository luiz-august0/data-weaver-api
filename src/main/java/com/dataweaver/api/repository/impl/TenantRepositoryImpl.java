package com.dataweaver.api.repository.impl;

import com.dataweaver.api.repository.TenantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {

    private final EntityManager entityManager;

    @Override
    public Boolean existsSchemaByName(String name) {
        Query query = entityManager.createNativeQuery(
                "" +
                        " select schema_name as schema from information_schema.schemata " +
                        " where schema_name = :name limit 1"
        );

        query.setParameter("name", name);

        return !query.getResultList().isEmpty();
    }

}
