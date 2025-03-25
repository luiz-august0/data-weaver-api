package com.dataweaver.api.model.entities;

import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilter;
import com.dataweaver.api.model.enums.EnumReportFilterType;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "report_filter")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportFilter extends AbstractEntity implements IReportFilter {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_report_filter", sequenceName = "gen_id_report_filter", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_report_filter")
    private Integer id;

    @JoinColumn(name = "id_report", referencedColumnName = "id")
    @ManyToOne
    private Report report;

    @Column(name = "label")
    private String label;

    @Column(name = "parameter", nullable = false)
    private String parameter;

    @Column(name = "type", nullable = false)
    private EnumReportFilterType type;

    @Column(name = "sql", nullable = false)
    private String sql;

    @Column(name = "standard_value")
    private String standardValue;

    @Column(name = "sort", nullable = false)
    private Integer sort;

    @Override
    public String getPortugueseClassName() {
        return "filtro de relatório";
    }

}