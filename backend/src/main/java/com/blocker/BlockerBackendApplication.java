package com.blocker;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blocker.service.FundingService;

@SpringBootApplication
public class BlockerBackendApplication implements CommandLineRunner{
	
	@Autowired
	FundingService fundingService;
	@PostConstruct
	public void started() throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println("현재시각 : " + new Date());
	}
	public static void main(String[] args) {
		SpringApplication.run(BlockerBackendApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		fundingService.makeAllTask();
	}

}
