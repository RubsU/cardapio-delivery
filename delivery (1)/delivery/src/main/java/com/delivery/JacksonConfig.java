package com.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    // Configura serialização lazy para evitar loops infinitos no JSON
    // As anotações @JsonIgnoreProperties nas entidades cuidam disso
}
