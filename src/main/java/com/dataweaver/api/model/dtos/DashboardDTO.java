package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.Dashboard;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class DashboardDTO extends AbstractDTO<Dashboard> {

    private Integer id;

    private String name;

    private String title;

    private List<DashboardReportDTO> reports;

    private List<DashboardLinkDTO> links;

}