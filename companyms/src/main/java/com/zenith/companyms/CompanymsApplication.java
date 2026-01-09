package com.zenith.companyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanymsApplication {

	public static void main(String[] args) {
		java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
		SpringApplication.run(CompanymsApplication.class, args);
	}

}
