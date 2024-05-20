package com.scrimmage.demo.spring;

import com.scrimmage.demo.spring.controller.TestController;
import com.scrimmage.spring.ScrimmageBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {ScrimmageBasePackage.class, TestController.class})
public class DemoSpringApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoSpringApplication.class, args);
  }

}