package com.candela.workflow.job;

import com.candela.workflow.bean.Department;
import com.candela.workflow.bean.HrmDepartment;
import com.candela.workflow.bean.HrmResource;
import com.candela.workflow.bean.ScheduleSign;
import com.candela.workflow.service.DepartmentService;
import com.candela.workflow.service.OverTimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.interfaces.schedule.BaseCronJob;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class DepartmentCronJob extends BaseCronJob {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void execute() {
        log.info("开始执行OverTimeCronJob");

        DepartmentService service = new DepartmentService();
        Map<Integer, Department> departments = service.getDepartments();
        List<HrmResource> list = service.getResource(departments);
        service.execute(list);
       /*for (HrmResource resource: list){
           log.info(resource);
       }*/

        log.info("结束OverTimeCronJob");


    }
}
