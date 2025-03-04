package com.dataweaver.api.controller.backoffice;

import com.dataweaver.api.controller.backoffice.interfaces.IHealthCheckController;
import com.dataweaver.api.utils.DateUtil;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements IHealthCheckController {

    @Override
    public String check() {
        return DateUtil.formatDDMMYYYYHHMMSS(DateUtil.getDate());
    }

}
