package com.reckyphiter.crudworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author Recky Phiter
 */
@SpringBootApplication
@ComponentScan
@EntityScan
@EnableJpaRepositories
@EnableScheduling
public class CrudWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudWorkerApplication.class, args);
	}
}
