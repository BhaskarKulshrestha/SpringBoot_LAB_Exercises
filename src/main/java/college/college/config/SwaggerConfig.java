package college.college.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("College Management System API")
                        .version("1.0")
                        .description("API Documentation for managing lecturers in the college system.")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@college.com")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Environment")));
    }
}
