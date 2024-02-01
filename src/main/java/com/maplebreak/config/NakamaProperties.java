package com.maplebreak.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nakama")
public record NakamaProperties(String host, Integer port, String serverKey, boolean ssl) {
}
