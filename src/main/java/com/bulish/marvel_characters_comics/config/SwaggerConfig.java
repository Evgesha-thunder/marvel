package com.bulish.marvel_characters_comics.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
  /*  private ApiInfo apiEndPointInfo(){
        return new ApiInfoBuilder().title("REST API MARVEL")
                .description("Documentation Marvel API")
                .contact(new Contact("Bulish Evgenia",
                        "https://github.com/Evgesha-thunder", "Bulish2015@yandex.ru"))
                .license("Apache 2.0")
                .licenseUrl("http://springdoc.org")
                .version("0.0.1")
                .build();
    }*/

  /*  @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("Comic")
                .pathsToMatch("/v1/public.*")
                .build();
    }

   @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Application Marvel API")
                .version("1.0")
                .description("Spring Boot Swagger Marvel Api")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org"))
                .contact(new Contact().name("Evgenia Bulish")
                        .email("Bulish2015@yandex.ru")))
                .servers(List.of(new Server().url("http://localhost:8080")
                                .description("Dev service")
                      ));
    }*/

