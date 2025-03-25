package com.dataweaver.api.model.entities;

import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "dashboard_report")
@EqualsAndHashCode(of = "id", callSuper = false)
public class DashboardReport extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_dashboard_report", sequenceName = "gen_id_dashboard_report", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_dashboard_report")
    private Integer id;

    @JoinColumn(name = "id_dashboard", referencedColumnName = "id")
    @ManyToOne
    private Dashboard dashboard;

    @JoinColumn(name = "id_report", referencedColumnName = "id")
    @ManyToOne
    private Report report;

    @Column(name = "sort", nullable = false)
    private Integer sort;

    public String getPortugueseClassName() {
        return "vínculo de relatório e dashboard";
    }

}