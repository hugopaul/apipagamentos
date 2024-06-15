package br.com.solidtechsolutions.apipagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApipagamentosApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApipagamentosApplication.class, args);
	}
}