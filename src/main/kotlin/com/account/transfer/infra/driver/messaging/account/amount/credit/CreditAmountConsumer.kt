package com.account.transfer.infra.driver.messaging.account.amount.credit

import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.rabbitmq.client.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput

@Component
class CreditAmountConsumer (
    private val creditAmount: CreditAmount
) {
    private val logger: Logger = LoggerFactory.getLogger(CreditAmountConsumer::class.java)

    @RabbitListener(queues = ["\${queue.credit.amount}"], ackMode = "AUTO", errorHandler = "accountMessagingExceptionHandler")
    fun creditAmount(input: CreditAmountInputMessage, message: Message, channel: Channel) {
        logger.info(
            "Received request to credit amount ${input.amount} to account ${input.accountId}"
        )

        creditAmount.execute(
            CreditAmountInput(
                input.accountId,
                input.amount
            )
        )

        logger.info(
            "The amount of ${input.amount} credited into account ${input.accountId} successfully"
        )
    }

}