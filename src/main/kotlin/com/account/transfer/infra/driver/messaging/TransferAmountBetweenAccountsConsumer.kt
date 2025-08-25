package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.infra.driver.messaging.input.TransferAmountInputMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.account.transfer.application.usecase.ammount.transfer.Input as TransferAmountInput

@Component
class TransferAmountBetweenAccountsConsumer (
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {
    private val logger: Logger = LoggerFactory.getLogger(TransferAmountBetweenAccountsConsumer::class.java)

    @RabbitListener(queues = ["\${queue.transfer.amount.between.accounts}"], ackMode = "AUTO")
    fun transferAmountBetweenAccounts(input: TransferAmountInputMessage) {
        logger.info(
            "Received request to transfer amount ${input.amount} from account ${input.from} to account ${input.to}"
        )

        try {
            transferAmountBetweenAccounts.execute(
                TransferAmountInput(
                    input.from,
                    input.to,
                    input.amount
                )
            )
        } catch (e: Exception) {
            logger.error(
                "Error to transfer amount ${input.amount} from account ${input.from} to account ${input.to}: ${e.message}"
            )
        }
    }

}