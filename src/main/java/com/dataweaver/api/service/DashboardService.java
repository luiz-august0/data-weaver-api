package com.dataweaver.api.service;

import com.dataweaver.api.model.entities.Dashboard;
import com.dataweaver.api.model.entities.DashboardLink;
import com.dataweaver.api.model.entities.DashboardReport;
import com.dataweaver.api.repository.DashboardRepository;
import com.dataweaver.api.utils.Utils;
import com.dataweaver.api.validators.DashboardValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    @Override
    public Dashboard insert(Dashboard dashboard) {
        updateLists(dashboard);

        return super.insert(dashboard);
    }

    @Override
    public Dashboard update(Integer id, Dashboard dashboard) {
        updateLists(dashboard);

        return super.update(id, dashboard);
    }

    private void updateLists(Dashboard dashboard) {
        dashboard.setLinks(Utils.nvl(dashboard.getLinks(), new ArrayList<DashboardLink>())
                .stream()
                .peek(link -> {
                    link.setDashboard(dashboard);
                })
                .collect(Collectors.toList())
        );

        dashboard.setReports(Utils.nvl(dashboard.getReports(), new ArrayList<DashboardReport>())
                .stream()
                .peek(report -> {
                    report.setDashboard(dashboard);
                })
                .collect(Collectors.toList())
        );
    }
}