package com.example.domaindrivendesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DomainDrivenDesignApplication {

  public static void main(String[] args) {
    SpringApplication.run(DomainDrivenDesignApplication.class, args);
  }

}
