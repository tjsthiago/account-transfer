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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Value("\${exchange.account.created}")
    private lateinit var accountCreatedExchange: String

    @Value("\${queue.account.created}")
    private lateinit var accountCreatedQueue: String

    @Value("\${routing.key.account.created}")
    private lateinit var accountCreatedRoutingKey: String

    @Value("\${exchange.amount.credited}")
    private lateinit var amountCreditedExchange: String

    @Value("\${queue.amount.credited}")
    private lateinit var amountCreditedQueue: String

    @Value("\${routing.amount.credited}")
    private lateinit var amountCreditedRoutingKey: String

    @Value("\${exchange.amount.between.accounts.transferred}")
    private lateinit var amountBetweenAccountsTransferredExchange: String

    @Value("\${queue.amount.between.accounts.transferred}")
    private lateinit var amountBetweenAccountsTransferredQueue: String

    @Value("\${routing.amount.between.accounts.transferred}")
    private lateinit var amountBetweenAccountsTransferredKey: String

    @Bean
    fun accountCreatedQueue(): Queue {
        return Queue(accountCreatedQueue)
    }

    @Bean
    fun accountCreatedExchange(): DirectExchange {
        return DirectExchange(accountCreatedExchange)
    }

    @Bean
    fun amountCreditedQueue(): Queue {
        return Queue(amountCreditedQueue)
    }

    @Bean
    fun amountCreditedExchange(): DirectExchange {
        return DirectExchange(amountCreditedExchange)
    }

    @Bean
    fun amountBetweenAccountsTransferredQueue(): Queue {
        return Queue(amountBetweenAccountsTransferredQueue)
    }

    @Bean
    fun amountBetweenAccountsTransferredExchange(): DirectExchange {
        return DirectExchange(amountBetweenAccountsTransferredExchange)
    }

    @Bean
    fun accountCreatedBinding(): Binding {
        return BindingBuilder
            .bind(accountCreatedQueue())
            .to(accountCreatedExchange())
            .with(accountCreatedRoutingKey)
    }

    @Bean
    fun amountCreditedBinding(): Binding {
        return BindingBuilder
            .bind(amountCreditedQueue())
            .to(amountCreditedExchange())
            .with(amountCreditedRoutingKey)
    }

    @Bean
    fun amountBetweenAccountsTransferredBinding(): Binding {
        return BindingBuilder
            .bind(amountBetweenAccountsTransferredQueue())
            .to(amountBetweenAccountsTransferredExchange())
            .with(amountBetweenAccountsTransferredKey)
    }

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