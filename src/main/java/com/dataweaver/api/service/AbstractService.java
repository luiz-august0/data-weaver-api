package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumResourceInactiveException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.dataweaver.api.infrastructure.specs.builders.SpecificationBuilder;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import com.dataweaver.api.pattern.validators.AbstractValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractService
        <Repository extends JpaRepository<Entity, Integer> & PagingAndSortingRepository<Entity, Integer> & JpaSpecificationExecutor<Entity>,
                Entity extends AbstractEntity, Validator extends AbstractValidator<Entity>> {
    private final Repository repository;

    public final Entity entity;

    private final Validator validator;

    @Autowired
    private ApplicationContext applicationContext;

    AbstractService(Repository repository, Entity entity, Validator validator) {
        this.repository = repository;
        this.entity = entity;
        this.validator = validator;
    }

    public <Service extends AbstractService> Service getServiceBean(Class<? extends AbstractEntity> entityClass) {
        try {
            Class<? extends AbstractService> serviceClass = entityClass.getDeclaredConstructor().newInstance().getServiceClass();

            return (Service) applicationContext.getBean(serviceClass);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public List<Entity> findAll() {
        return repository.findAll();
    }

    public List<Entity> findAllFiltered(Pageable pageable, Map<String, Object> filters) {
        return repository.findAll(SpecificationBuilder.toSpec(filters), pageable.getSort());
    }

    public Page<Entity> findAllFilteredAndPageable(Pageable pageable, Map<String, Object> filters) {
        return repository.findAll(SpecificationBuilder.toSpec(filters), pageable);
    }

    public Entity findAndValidate(Integer id) {
        Optional object = repository.findById(id);

        if (object.isEmpty()) {
            throw new ApplicationGenericsException(EnumResourceNotFoundException.RESOURCE_NOT_FOUND, entity.getPortugueseClassName(), id);
        }

        return (Entity) object.get();
    }

    public Entity findAndValidateActive(Integer id, Boolean returnObjectName) {
        Entity entityObject = this.findAndValidate(id);

        try {
            Field field = entityObject.getClass().getDeclaredField("active");
            field.setAccessible(true);
            Boolean active = (Boolean) field.get(entityObject);

            if (active.equals(false)) {
                if (returnObjectName) {
                    throw new ApplicationGenericsException(
                            EnumResourceInactiveException.RESOURCE_INACTIVE,
                            entity.getPortugueseClassName(),
                            entityObject.getObjectName()
                    );
                } else {
                    throw new ApplicationGenericsException(EnumResourceInactiveException.RESOURCE_INACTIVE, entity.getPortugueseClassName(), id);
                }
            }
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo active");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo active da classe " + entity.getClass().getName());
        }

        return entityObject;
    }

    public <GenericEntity extends AbstractEntity> GenericEntity findAndValidateGeneric(Class<? extends AbstractEntity> entityClass, Integer id) {
        return (GenericEntity) getServiceBean(entityClass).findAndValidate(id);
    }

    public <GenericEntity extends AbstractEntity> GenericEntity findAndValidateActiveGeneric(Class<? extends AbstractEntity> entityClass, Integer id, Boolean returnObjectName) {
        return (GenericEntity) getServiceBean(entityClass).findAndValidateActive(id, returnObjectName);
    }

    @Transactional
    public Entity insert(Entity entityObject) {
        validator.validate(entityObject);

        return repository.save(entityObject);
    }

    @Transactional
    public Entity activateInactivate(Integer id, Boolean active) {
        Entity entityObject = this.findAndValidate(id);

        try {
            Field field = entityObject.getClass().getDeclaredField("active");
            field.setAccessible(true);
            field.set(entityObject, active);
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo active");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo active da classe " + entity.getClass().getName());
        }

        return repository.save(entityObject);
    }

    @Transactional
    public Entity update(Integer id, Entity entityObject) {
        this.findAndValidate(id);

        try {
            Class<?> objectClass = entityObject.getClass();
            Field field = objectClass.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entityObject, id);
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo ID");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo id da classe " + entity.getClass().getName());
        }

        validator.validate(entityObject);

        return repository.save(entityObject);
    }

}
