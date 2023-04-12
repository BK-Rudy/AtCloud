package br.edu.infnet.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtJavaCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtJavaCloudApplication.class, args);
	}

}