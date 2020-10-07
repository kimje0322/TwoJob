package com.blocker.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


import com.blocker.dto.InvestmentDto;
import com.blocker.repository.InvestmentRepository;
import com.blocker.util.BeanUtils;


public class ScheduleTask extends Thread {
	FundingService fundingService;
	InvestmentRepository investmentRepository;
	private int mYear, mMonth, mDay;
	private String campaignId;
	public ScheduleTask( int year, int month, int day, String campaignId){
		mYear = year;
		mMonth = month;
		mDay = day;
		this.campaignId = campaignId;
		fundingService = (FundingService) BeanUtils.getBean("fundingServiceImpl");
		investmentRepository = (InvestmentRepository) BeanUtils.getBean("investmentRepository");
	}
	@Override
	public void run() {
		try {
			long value = timeUntil(mYear, mMonth, mDay);
			if(value <= 0) {value = 0;}
			sleep(value);
			work();
		} catch ( InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void work(){
		try {
			BigDecimal result = fundingService.getfundingrate(campaignId);
			if(result.compareTo(new BigDecimal("100")) == -1) {
				System.out.println("환불");
				fundingService.reFund(campaignId);
			}else {
				System.out.println("펀딩자 수령");
				fundingService.receiveFund(campaignId);
			}
			Optional<InvestmentDto> in =investmentRepository.findById(campaignId);
			InvestmentDto invest = in.get();
			invest.setIsfinish(true);
			investmentRepository.save(invest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public long timeUntil( int year, int month, int day){
		Date now = new Date();		
		Calendar calUntil = Calendar.getInstance();
		calUntil.set( Calendar.YEAR, year);
		calUntil.set( Calendar.MONTH, month-1);
		calUntil.set( Calendar.DAY_OF_MONTH, day);
		calUntil.set( Calendar.HOUR_OF_DAY, 3);
		calUntil.set( Calendar.MINUTE, 13);
		calUntil.set( Calendar.SECOND, 0);
		Date until = calUntil.getTime();
		long sleep = until.getTime() - now.getTime();
		System.out.println(until.getTime() + " " + now.getTime() + " sleep ? = " + sleep);
		return sleep;
	}
}
