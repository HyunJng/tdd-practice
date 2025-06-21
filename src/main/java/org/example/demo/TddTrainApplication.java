package org.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "TDD Practice Project",
        version = "v1",
        description = "TDD 연습 토이 프로젝트"
))
public class TddTrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(TddTrainApplication.class, args);
    }

}
