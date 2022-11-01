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

	/*@Autowired
	static UserRepository userRepository;*/

	public static void main(String[] args) {
		SpringApplication.run(ThedoorApplication.class, args);
		//UserImpl impl = new UserImpl(userRepository);
	}



}

class UserImpl {

	public UserImpl(UserRepository userRepository) {
		saveUser(userRepository);
	}

	void saveUser(UserRepository userRepository) {

		/*userRepository.save(new User("JUAN ARANGO", "jarango",
				"1312", "juancamiloarango@gmail.com", "admin",
				"346456220"));*/
	}
}
