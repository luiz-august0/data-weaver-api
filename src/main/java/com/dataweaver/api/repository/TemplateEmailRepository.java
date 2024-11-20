package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.TemplateEmail;
import com.dataweaver.api.model.enums.EnumTemplateEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateEmailRepository extends JpaRepository<TemplateEmail, Integer> {

    Optional<TemplateEmail> findFirstByTemplateEmail(EnumTemplateEmail templateEmail);

}
