package com.emailvision.sample.spring.vertx.vertx;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    public void start() {
        LOGGER.info("start.");
        vertx.createHttpServer().requestHandler(req -> {
            LOGGER.info("Received a http request");
            vertx.eventBus().send("customer", "findAll req", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        }).listen(8080);

        LOGGER.info("Started HttpServer(port=8080).");
    }
}