package com.candela.workflow.job;

import com.candela.workflow.bean.ScheduleSign;
import com.candela.workflow.service.OverTimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.interfaces.schedule.BaseCronJob;

import java.util.List;

public class OverTimeCronJob extends BaseCronJob {
    private Log log = LogFactory.getLog(this.getClass());
    private OverTimeService service = new OverTimeService();

    @Override
    public void execute() {
        log.info("开始执行OverTimeCronJob");

        List<ScheduleSign> list = service.getList();

        service.handle(list);


        log.info("结束OverTimeCronJob");


    }
}
