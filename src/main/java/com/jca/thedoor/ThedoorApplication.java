package com.jca.thedoor;

import com.jca.thedoor.repository.mongodb.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//to solve Failed to start bean 'documentationPluginsBootstrapper'
@EnableWebMvc
@SpringBootApplication
@EnableMongoRepositories
public class ThedoorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThedoorApplication.class, args);
	}
}
