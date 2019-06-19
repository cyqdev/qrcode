package com.hengan.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class QrcodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(QrcodeApplication.class, args);
	}
}

