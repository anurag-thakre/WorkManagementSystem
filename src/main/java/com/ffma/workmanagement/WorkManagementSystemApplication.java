package com.ffma.workmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WorkManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkManagementSystemApplication.class, args);
	}

}
