package pl.applause.jakub.dabrowski.tester.matching.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TesterBackendApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(TesterBackendApplication.class, args);
	}

}
