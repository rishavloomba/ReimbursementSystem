package com.quikr;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@EnableTransactionManagement
public class MainApp {

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
		
		
	}
}
