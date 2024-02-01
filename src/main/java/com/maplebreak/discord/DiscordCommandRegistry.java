package com.maplebreak.discord;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.maplebreak.discord.cmd.SlashCommand;
import com.maplebreak.config.DiscordProperties;
import com.maplebreak.discord.cmd.SlashCommandGroup;
import discord4j.common.JacksonResources;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordCommandRegistry implements ApplicationRunner {
    private final List<SlashCommand> commandList;
    private final RestClient restClient;
    private final DiscordProperties discordProperties;

    @Override
    public void run(ApplicationArguments args) throws IOException {
//        this.restClient.getApplicationService()
//                .bulkOverwriteGuildApplicationCommand(discordProperties.applicationId(), discordProperties.guildId(), Collections.singletonList(ApplicationCommandRequest.builder()
//                                .name("match")
//                                .type(ApplicationCommandOption.Type.SUB_COMMAND_GROUP.getValue())
//                                .options(List.of(
//                                        ApplicationCommandOptionData.builder()
//                                                .name("list")
//                                                .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
//                                                .description("nakama list match command")
//                                                .build(),
//                                        ApplicationCommandOptionData.builder()
//                                                .name("create")
//                                                .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
//                                                .description("nakama create match command")
//                                                .addOption(ApplicationCommandOptionData.builder()
//                                                        .name("name")
//                                                        .type(ApplicationCommandOption.Type.STRING.getValue())
//                                                        .description("match name")
//                                                        .required(true)
//                                                        .build())
//                                                .build()
//                                ))
//                        .build()
//                ))
//                .doOnNext(ignore -> log.info("Successfully registered Guild Commands"))
//                .doOnError(e -> log.error("Failed to register guild commands", e))
//                .subscribe();

        if (CollectionUtils.isEmpty(commandList)) {
            log.warn("command list empty, skipped register at discord.");
            return;
        }

//        final List<ApplicationCommandRequest> commandRequestList = commandList.stream()
//                .map(it -> ApplicationCommandRequest.builder()
//                        .name(it.name())
//                        .description(it.description())
//                        .options(it.options())
//                        .build())
//                .map(it -> ((ApplicationCommandRequest) it))
//                .toList();

        final List<ApplicationCommandRequest> commandRequestList = new LinkedList<>();
        final PathMatchingResourcePatternResolver matcher = new PathMatchingResourcePatternResolver();
        for (Resource resource : matcher.getResources("commands/*.json")) {
            commandRequestList.add(JacksonResources.create().getObjectMapper().readValue(resource.getInputStream(), ApplicationCommandRequest.class));
        }

        this.restClient.getApplicationService()
                .bulkOverwriteGuildApplicationCommand(discordProperties.applicationId(), discordProperties.guildId(), commandRequestList)
                .doOnNext(ignore -> log.info("Successfully registered Guild Commands"))
                .doOnError(e -> log.error("Failed to register guild commands", e))
                .subscribe();
    }
}