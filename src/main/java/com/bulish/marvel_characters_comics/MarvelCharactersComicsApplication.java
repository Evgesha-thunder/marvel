package com.bulish.marvel_characters_comics;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class MarvelCharactersComicsApplication {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5433/marvel_characters_comics", "postgres", "04041995")
                .load();
        flyway.migrate();
        SpringApplication.run(MarvelCharactersComicsApplication.class, args);
    }

}
