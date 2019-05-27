package com.reckyphiter.crudproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Recky Phiter
 */
@SpringBootApplication
@ComponentScan
@EntityScan
@EnableJpaRepositories
public class CrudProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudProxyApplication.class, args);
	}
}
