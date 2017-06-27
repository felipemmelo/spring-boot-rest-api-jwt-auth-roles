package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 
@SpringBootApplication
@ServletComponentScan(value="security")
@ComponentScan({"services", "controller", "persistence", "security"})
@EnableJpaRepositories(basePackages={"repository"})
public class Application {
 
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
