package com.maplebreak.config;

import discord4j.common.ReactorResources;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
public class DiscordConfig {

    private final GatewayDiscordClient gatewayDiscordClient;

    private final RestClient restClient;

    public DiscordConfig(DiscordProperties props) {
        final HttpClient httpClient = HttpClient.create().compress(true).followRedirect(true).secure();
        final ExecutorService blockingTaskExecutorService = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
        final Scheduler blockingTaskScheduler = Schedulers.fromExecutorService(blockingTaskExecutorService, "discord-blocking-task-executor");
        final ReactorResources reactorResources = ReactorResources.builder()
                .httpClient(httpClient)
                .blockingTaskScheduler(blockingTaskScheduler)
                .build();

        this.gatewayDiscordClient = DiscordClientBuilder
                .create(props.token())
                .setReactorResources(reactorResources)
                .build()
                .gateway()
                .setInitialPresence(props::initialPresence)
                .login()
                .blockOptional()
                .orElseThrow();

        this.restClient = gatewayDiscordClient.getRestClient();
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return gatewayDiscordClient;
    }

    @Bean
    public RestClient restClient() {
        return restClient;
    }
}
