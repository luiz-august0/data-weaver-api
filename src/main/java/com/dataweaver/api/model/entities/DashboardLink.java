package com.dataweaver.api.model.entities;

import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "dashboard_link")
@EqualsAndHashCode(of = "id", callSuper = false)
public class DashboardLink extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_dashboard_link", sequenceName = "gen_id_dashboard_link", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_dashboard_link")
    private Integer id;

    @JoinColumn(name = "id_dashboard", referencedColumnName = "id")
    @ManyToOne
    private Dashboard dashboard;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "order", nullable = false)
    private Integer order;

    public String getPortugueseClassName() {
        return "dashboard link";
    }

}