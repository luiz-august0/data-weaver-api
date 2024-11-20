package com.dataweaver.api.model.entities;

import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "dashboard")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Dashboard extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_dashboard", sequenceName = "gen_id_dashboard", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_dashboard")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dashboard", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order")
    private List<DashboardReport> reports;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dashboard", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order")
    private List<DashboardLink> links;

    @Column(name = "main", nullable = false)
    private Boolean main;

    public String getPortugueseClassName() {
        return "dashboard";
    }

}