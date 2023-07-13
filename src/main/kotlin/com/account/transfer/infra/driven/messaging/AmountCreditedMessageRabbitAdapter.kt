package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountCreditedMessagePort
import com.account.transfer.domain.events.AmountCreditedEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
@Qualifier("AmountCreditedMessageRabbitAdapter")
class AmountCreditedMessageRabbitAdapter (
    private val rabbitTemplate: RabbitTemplate
) : AmountCreditedMessagePort {

    @Value("\${exchange.amount.credited}")
    private lateinit var amountCreditedExchange: String

    @Value("\${routing.amount.credited}")
    private lateinit var amountCreditedRoutingKey: String

    override fun send(event: AmountCreditedEvent) {
        rabbitTemplate.convertAndSend(
            amountCreditedExchange!!,
            amountCreditedRoutingKey!!,
            event
        )
    }
}