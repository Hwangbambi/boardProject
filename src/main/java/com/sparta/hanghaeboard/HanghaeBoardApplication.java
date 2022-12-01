package com.sparta.hanghaeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //timestamped 의 기능을 해줌
@SpringBootApplication
public class HanghaeBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanghaeBoardApplication.class, args);
	}

}
