package com.account.transfer.infra.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RabbitMQConfig {

    @Value("\${exchange.account.created}")
    private lateinit var accountCreatedExchange: String

    @Value("\${queue.account.created}")
    private lateinit var accountCreatedQueue: String

    @Value("\${routing.key.account.created}")
    private lateinit var accountCreatedRoutingKey: String

    @Bean
    fun accountCreatedQueue(): Queue {
        return Queue(accountCreatedQueue)
    }

    @Bean
    fun accountCreatedExchange(): DirectExchange {
        return DirectExchange(accountCreatedExchange)
    }

    @Bean
    fun accountCreatedBinding(): Binding {
        return BindingBuilder
            .bind(accountCreatedQueue())
            .to(accountCreatedExchange())
            .with(accountCreatedRoutingKey)
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun converter(): MessageConverter {
        val mapper = ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.ALWAYS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return Jackson2JsonMessageConverter(mapper)
    }

    @Bean
    fun amqpTemplate(connectionFactory: ConnectionFactory?): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory!!)
        rabbitTemplate.messageConverter = converter()
        return rabbitTemplate
    }
}