package com.dataweaver.api.model.entities;

import com.dataweaver.api.pattern.entities.AbstractEntity;
import com.dataweaver.api.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "report")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Report extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_report", sequenceName = "gen_id_report", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_report")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "sql", nullable = false)
    private String sql;

    @Column(name = "sql_totalizers")
    private String sqlTotalizers;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sort")
    private List<ReportColumn> columns;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sort")
    private List<ReportFilter> filters;

    @Transient
    private boolean hasTotalizers;

    @PostLoad
    private void postLoad() {
        this.hasTotalizers = StringUtil.isNotNullOrEmpty(this.sqlTotalizers);
    }

    @Override
    public String getPortugueseClassName() {
        return "relatório";
    }

}