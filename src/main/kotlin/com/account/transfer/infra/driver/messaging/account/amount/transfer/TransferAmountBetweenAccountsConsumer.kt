package com.account.transfer.infra.driver.messaging.account.amount.transfer

import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.rabbitmq.client.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.account.transfer.application.usecase.ammount.transfer.Input as TransferAmountInput

@Component
class TransferAmountBetweenAccountsConsumer (
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {
    private val logger: Logger = LoggerFactory.getLogger(TransferAmountBetweenAccountsConsumer::class.java)

    @RabbitListener(queues = ["\${queue.transfer.amount.between.accounts}"], ackMode = "AUTO", errorHandler = "accountMessagingExceptionHandler")
    fun transferAmountBetweenAccounts(input: TransferAmountInputMessage, message: Message, channel: Channel) {
        logger.info(
            "Received a request to transfer the amount of ${input.amount} from account ${input.from} to account ${input.to}"
        )

        transferAmountBetweenAccounts.execute(
            TransferAmountInput(
                input.from,
                input.to,
                input.amount
            )
        )

        logger.info(
            "Successfully transferred the amount of ${input.amount} from account ${input.from} to account ${input.to}"
        )
    }

}