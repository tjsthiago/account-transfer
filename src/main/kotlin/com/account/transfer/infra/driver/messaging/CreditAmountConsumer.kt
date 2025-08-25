package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.infra.driver.messaging.input.CreditAmountInputMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput

@Component
class CreditAmountConsumer (
    private val creditAmount: CreditAmount
) {
    private val logger: Logger = LoggerFactory.getLogger(CreditAmountConsumer::class.java)

    @RabbitListener(queues = ["\${queue.credit.amount}"], ackMode = "AUTO")
    fun creditAmount(input: CreditAmountInputMessage) {
        logger.info(
            "Received request to credit amount ${input.amount} to account ${input.accountId}"
        )

        try {
            creditAmount.execute(
                CreditAmountInput(
                    input.accountId,
                    input.amount
                )
            )
        } catch (e: Exception) {
            logger.error("Error processing credit amount request: ${e.message}", e)
        }
    }

}