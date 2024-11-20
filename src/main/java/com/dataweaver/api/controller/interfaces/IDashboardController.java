package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.DashboardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IDashboardController.PATH)
public interface IDashboardController extends IAbstractAllGetController<DashboardDTO> {

    String PATH = prefixPath + "/dashboard";

    @GetMapping("/main")
    DashboardDTO getMainDashboard();

}