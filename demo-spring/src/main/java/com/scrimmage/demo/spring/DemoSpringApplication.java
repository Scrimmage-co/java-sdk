package com.scrimmage.demo.spring;

import com.scrimmage.demo.spring.controller.TestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class DemoSpringApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoSpringApplication.class, args);
  }

}