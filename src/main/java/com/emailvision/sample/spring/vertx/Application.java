package com.emailvision.sample.spring.vertx;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_EMPTY_JSON_ARRAYS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_NULL_MAP_VALUES;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.emailvision.sample.spring.vertx.vertx.CustomerVerticle;
import com.emailvision.sample.spring.vertx.vertx.HttpServerVerticle;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    protected Environment environment;

    @Bean
    protected ObjectMapper objectMapper() {
        return new ObjectMapper().disable(INDENT_OUTPUT)
                                 .disable(WRITE_EMPTY_JSON_ARRAYS)
                                 .disable(WRITE_NULL_MAP_VALUES)
                                 .disable(FAIL_ON_EMPTY_BEANS)
                                 .enable(WRITE_DATES_AS_TIMESTAMPS)
                                 .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                                 .enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                                 .setSerializationInclusion(NON_NULL);
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .registerShutdownHook(true)
                .logStartupInfo(true)
                .showBanner(true)
                .run(args);

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(context.getBean(CustomerVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(HttpServerVerticle.class));
    }
}
