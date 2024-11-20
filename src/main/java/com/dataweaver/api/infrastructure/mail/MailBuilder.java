package com.dataweaver.api.infrastructure.mail;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.mail.interfaces.ITemplate;
import com.dataweaver.api.model.entities.TemplateEmail;
import com.dataweaver.api.model.enums.EnumTemplateEmail;
import com.dataweaver.api.repository.TemplateEmailRepository;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract class MailBuilder<Parameter> {

    private final TemplateEmail templateEmail;

    public MailBuilder(TemplateEmailRepository templateEmailRepository, EnumTemplateEmail template) {
        Optional<TemplateEmail> optionalTemplateEmail = templateEmailRepository.findFirstByTemplateEmail(template);

        if (optionalTemplateEmail.isPresent()) {
            this.templateEmail = optionalTemplateEmail.get();
        } else {
            throw new ApplicationGenericsException("Não foi possível encontrar o template: " + template.getValue());
        }
    }

    public abstract ITemplate build(Parameter parameter);

}
