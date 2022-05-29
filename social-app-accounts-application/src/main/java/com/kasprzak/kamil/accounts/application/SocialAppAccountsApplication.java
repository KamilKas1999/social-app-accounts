package com.kasprzak.kamil.accounts.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.kasprzak"})
@EntityScan({"com.kasprzak"})
@ComponentScan(basePackages = {"com.kasprzak"})
@EnableJpaRepositories(basePackages = {"com.kasprzak"})
public class SocialAppAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialAppAccountsApplication.class, args);
	}

}
