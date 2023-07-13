package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.domain.events.AccountCreatedEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
@Qualifier("AccountCreatedMessageRabbitAdapter")
class AccountCreatedMessageRabbitAdapter (
    private val rabbitTemplate: RabbitTemplate
) : AccountCreatedMessagePort {

    @Value("\${exchange.account.created}")
    private lateinit var accountCreatedExchange: String

    @Value("\${routing.key.account.created}")
    private lateinit var accountCreatedRoutingJsonKey: String

    override fun send(event: AccountCreatedEvent) {
        rabbitTemplate.convertAndSend(
            accountCreatedExchange,
            accountCreatedRoutingJsonKey,
            event
        )
    }
}