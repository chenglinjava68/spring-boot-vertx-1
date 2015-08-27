package com.emailvision.sample.spring.vertx.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailvision.sample.spring.vertx.jpa.domain.Customer;
import com.emailvision.sample.spring.vertx.service.CustomerService;

@Component
public class CustomerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerVerticle.class);

    @Autowired
    private CustomerService sqlTemplateService;

    @Autowired
    private ObjectMapper jsonMapper;

    public void start() {
        LOGGER.info("start.");

        vertx.eventBus().consumer("customer", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            final Iterable<Customer> all = this.sqlTemplateService.findAll();

            try {
                String json = this.jsonMapper.writeValueAsString(all);
                message.reply(json);
            } catch (JsonProcessingException e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}