package com.dataweaver.api.infrastructure.context;

import com.dataweaver.api.model.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

    private static final ThreadLocal<EntityManager> currentEntityManager = ThreadLocal.withInitial(() -> null);

    public static void setEntityManager(EntityManager entityManager) {
        currentEntityManager.set(entityManager);
    }

    public static EntityManager getCurrentEntityManager() {
        return currentEntityManager.get();
    }

    public static void clearEntityManager() {
        currentEntityManager.remove();
    }

    public static User getUserByContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
