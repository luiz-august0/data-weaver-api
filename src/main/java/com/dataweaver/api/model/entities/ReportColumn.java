package com.dataweaver.api.model.entities;

import com.dataweaver.api.infrastructure.reports.interfaces.IReportColumn;
import com.dataweaver.api.model.enums.EnumColumnAlign;
import com.dataweaver.api.model.enums.EnumColumnFormat;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "report_column")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportColumn extends AbstractEntity implements IReportColumn {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_report_column", sequenceName = "gen_id_report_column", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_report_column")
    private Integer id;

    @JoinColumn(name = "id_report", referencedColumnName = "id")
    @ManyToOne
    private Report report;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "html", nullable = false)
    private String html;

    @Column(name = "header_name", nullable = false)
    private String headerName;

    @Column(name = "header_align", nullable = false)
    private EnumColumnAlign headerAlign;

    @Column(name = "align", nullable = false)
    private EnumColumnAlign align;

    @Column(name = "sort", nullable = false)
    private Integer sort;

    @Column(name = "format", nullable = false)
    private EnumColumnFormat format;

    @Override
    public String getPortugueseClassName() {
        return "coluna de relatório";
    }

}