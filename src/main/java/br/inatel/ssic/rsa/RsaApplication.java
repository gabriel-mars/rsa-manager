package br.inatel.ssic.rsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsaApplication.class, args);
	}

}
