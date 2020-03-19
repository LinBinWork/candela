package com.candela.workflow.job;

import com.candela.workflow.bean.HrmDepartment;
import com.candela.workflow.bean.ScheduleSign;
import com.candela.workflow.service.DepartmentService;
import com.candela.workflow.service.OverTimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.interfaces.schedule.BaseCronJob;

import java.util.List;

public class DepartmentCronJob extends BaseCronJob {
    private Log log = LogFactory.getLog(this.getClass());
    private DepartmentService service = new DepartmentService();

    @Override
    public void execute() {
        log.info("开始执行OverTimeCronJob");

        List<HrmDepartment> list = service.getList();

        service.execute(list);



        log.info("结束OverTimeCronJob");


    }
}
