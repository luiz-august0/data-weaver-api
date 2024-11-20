package com.dataweaver.api.model.entities;

import com.dataweaver.api.model.enums.EnumTemplateEmail;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "template_email", schema = "public")
@EqualsAndHashCode(of = "id", callSuper = false)
public class TemplateEmail extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_template_email", sequenceName = "gen_id_template_email", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_template_email")
    private Integer id;

    @Column(name = "template_email", nullable = false, unique = true)
    private EnumTemplateEmail templateEmail;

    @Column(name = "html", nullable = false)
    private String html;

    @Override
    public String getPortugueseClassName() {
        return "template de email";
    }

}