package com.maplebreak.config;

import com.heroiclabs.nakama.Client;
import com.heroiclabs.nakama.DefaultClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NakamaConfig {

    @Bean
    public Client nakamaClient(NakamaProperties properties) {
        return new DefaultClient(properties.serverKey(), properties.host(), properties.port(), properties.ssl());
    }
}
