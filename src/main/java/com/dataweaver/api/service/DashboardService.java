package com.dataweaver.api.service;

import com.dataweaver.api.model.entities.Dashboard;
import com.dataweaver.api.repository.DashboardRepository;
import com.dataweaver.api.validators.DashboardValidator;
import org.springframework.stereotype.Service;

@Service
public class DashboardService extends AbstractService<DashboardRepository, Dashboard, DashboardValidator> {

    private final DashboardRepository dashboardRepository;

    DashboardService(DashboardRepository dashboardRepository) {
        super(dashboardRepository, new Dashboard(), new DashboardValidator());
        this.dashboardRepository = dashboardRepository;
    }

    public Dashboard getMainDashboard() {
        return dashboardRepository.findFirstByMainTrue();
    }

}