package com.nttdata.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Account application, enable eureka client.
 */
@EnableEurekaClient
@SpringBootApplication
public class AccountsApplication {
  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }
}
