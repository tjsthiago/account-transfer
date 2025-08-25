package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferredMessagePort
import com.account.transfer.domain.events.AmountBetweenAccountsTransferedEvent
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

    @Value("\${exchange.amount.between.accounts.transfered}")
    private lateinit var amountBetweenAccountsTransferedExchange: String

    @Value("\${routing.amount.between.accounts.transfered}")
    private lateinit var amountBetweenAccountsTransferedKey: String

    override fun send(event: AmountBetweenAccountsTransferedEvent) {
        rabbitTemplate.convertAndSend(
            amountBetweenAccountsTransferedExchange,
            amountBetweenAccountsTransferedKey,
            event
        )
    }
}