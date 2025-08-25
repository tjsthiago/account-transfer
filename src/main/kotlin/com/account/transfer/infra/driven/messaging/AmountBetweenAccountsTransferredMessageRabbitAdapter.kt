package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferredMessagePort
import com.account.transfer.domain.events.AmountBetweenAccountsTransferredEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
@Qualifier("AmountBetweenAccountsTransferredMessageRabbitAdapter")
class AmountBetweenAccountsTransferredMessageRabbitAdapter (
    private val rabbitTemplate: RabbitTemplate
) : AmountBetweenAccountsTransferredMessagePort {

    @Value("\${exchange.amount.between.accounts.transferred}")
    private lateinit var amountBetweenAccountsTransferredExchange: String

    @Value("\${routing.amount.between.accounts.transferred}")
    private lateinit var amountBetweenAccountsTransferredKey: String

    override fun send(event: AmountBetweenAccountsTransferredEvent) {
        rabbitTemplate.convertAndSend(
            amountBetweenAccountsTransferredExchange,
            amountBetweenAccountsTransferredKey,
            event
        )
    }
}