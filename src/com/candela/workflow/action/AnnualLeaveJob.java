package com.candela.workflow.action;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;

import com.candela.workflow.service.VocationService;

import weaver.common.DateUtil;
import weaver.interfaces.schedule.BaseCronJob;

public class AnnualLeaveJob  extends  BaseCronJob {
	@Override
	public void execute() {
		VocationService vocationService = new VocationService();
		vocationService.execute();

		String firstDayOfYear = DateUtil.getFirstDayOfYear();
		String lastDayOfYear = DateUtil.getLastDayOfYear();
		int compDate = DateUtil.compDate(firstDayOfYear, lastDayOfYear);
		String addDate = DateUtil.addDate(firstDayOfYear, 6);
		Calendar calendar = DateUtil.getCalendar();
		LocalDate localDate = LocalDate.of(1994, 11, 02);

	}

}
