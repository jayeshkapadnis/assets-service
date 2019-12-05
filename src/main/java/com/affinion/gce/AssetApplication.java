package com.affinion.gce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableJpaRepositories
public class AssetApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class);
    }

}
