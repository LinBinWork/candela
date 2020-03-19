package com.candela.workflow.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.candela.workflow.bean.PersonVocation;
import com.candela.workflow.bean.Vocation;
import com.candela.workflow.bean.YearVocation;
import com.candela.workflow.service.VocationService;
import com.candela.workflow.service.YearVocationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;

public class AnnualLeaveJob  extends  BaseCronJob {
	@Override
	public void execute() {
		VocationService vocationService = new VocationService();
		vocationService.execute();

	}

}
