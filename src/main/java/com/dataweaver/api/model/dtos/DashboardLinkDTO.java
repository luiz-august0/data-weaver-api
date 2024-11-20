package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.DashboardLink;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class DashboardLinkDTO extends AbstractDTO<DashboardLink> {

    private Integer id;

    private String title;

    private String link;

    private Integer order;

}