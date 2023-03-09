package com.globallogic.xlstodatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class XlstodatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(XlstodatabaseApplication.class, args);
	}

}
