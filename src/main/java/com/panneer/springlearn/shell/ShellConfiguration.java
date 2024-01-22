package com.panneer.springlearn.shell;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ConditionalOnProperty(name = "shell.enabled", matchIfMissing = true)
public class ShellConfiguration {

    @Bean
    ShellCommandClient shellCommandClient(){
        var client = WebClient.builder().baseUrl("https://icanhazdadjoke.com").defaultHeader("Accept","application/json").build();
        var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(ShellCommandClient.class);
    }
}
