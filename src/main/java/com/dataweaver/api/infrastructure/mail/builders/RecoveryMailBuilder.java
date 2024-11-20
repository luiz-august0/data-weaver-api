package com.dataweaver.api.infrastructure.mail.builders;

import com.dataweaver.api.infrastructure.mail.MailBuilder;
import com.dataweaver.api.infrastructure.mail.interfaces.ITemplate;
import com.dataweaver.api.model.entities.TemplateEmail;
import com.dataweaver.api.model.enums.EnumTemplateEmail;
import com.dataweaver.api.repository.TemplateEmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RecoveryMailBuilder extends MailBuilder<String> {

    @Value("${app.url}")
    private String url;

    public RecoveryMailBuilder(TemplateEmailRepository templateEmailRepository) {
        super(templateEmailRepository, EnumTemplateEmail.RECOVERY);
    }

    @Override
    public ITemplate build(String token) {
        TemplateEmail templateEmail = getTemplateEmail();

        String html = templateEmail.getHtml().replaceAll("::url_redirect_signature::", url + "/login/recuperacao/" + token);

        return new ITemplate() {
            @Override
            public String getHtml() {
                return html;
            }

            @Override
            public String getSubject() {
                return templateEmail.getTemplateEmail().getSubject();
            }
        };
    }

}
