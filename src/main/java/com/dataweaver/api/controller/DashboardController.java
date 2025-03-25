package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IDashboardController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.DashboardDTO;
import com.dataweaver.api.model.entities.Dashboard;
import com.dataweaver.api.service.DashboardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController extends AbstractAllController<DashboardService, DashboardDTO, Dashboard> implements IDashboardController {

    private final DashboardService dashboardService;

    DashboardController(DashboardService dashboardService) {
        super(dashboardService, new DashboardDTO(), new Dashboard());
        this.dashboardService = dashboardService;
    }

    @Override
    public DashboardDTO getMainDashboard() {
        return Converter.convertEntityToDTO(dashboardService.getMainDashboard(), DashboardDTO.class);
    }

}
