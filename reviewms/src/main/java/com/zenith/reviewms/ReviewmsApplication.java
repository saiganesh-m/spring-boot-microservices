package com.zenith.reviewms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewmsApplication {

	public static void main(String[] args) {
		java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
		SpringApplication.run(ReviewmsApplication.class, args);
	}

}
