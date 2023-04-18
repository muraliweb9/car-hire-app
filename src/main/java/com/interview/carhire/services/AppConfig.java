package com.interview.carhire.services;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Should be WebClient.Builder
     * If it is WebClient.builder().build() -> WebClient then Eureka lookup fails
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Server server = new Server();
        server.setUrl("localhost:" + serverPort);
        server.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("muraliweb@hotmail.com");
        contact.setName("Murali Karunanithy");
        contact.setUrl("https://cap-hire-app");

        License mitLicense = new License().name("Open Source").url("https://spring.io");

        Info info = new Info()
                .title("Car Hire App")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage car hires")
                .termsOfService("https://cap-hire-app/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(Arrays.asList(server));
    }
}