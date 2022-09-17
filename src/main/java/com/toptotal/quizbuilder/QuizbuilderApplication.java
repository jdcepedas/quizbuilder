package com.toptotal.quizbuilder;

import com.toptotal.quizbuilder.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class QuizbuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizbuilderApplication.class, args);
	}

}
