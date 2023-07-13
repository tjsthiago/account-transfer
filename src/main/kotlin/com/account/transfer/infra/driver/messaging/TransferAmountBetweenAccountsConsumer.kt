package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountInput
import com.account.transfer.infra.driver.messaging.input.TransferAmountInputMessage
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TransferAmountBetweenAccountsConsumer (
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {

    @RabbitListener(queues = ["\${queue.transfer.amount.between.accounts}"], ackMode = "AUTO")
    fun transferAmountBetweenAccounts(input: TransferAmountInputMessage) {
        transferAmountBetweenAccounts.execute(
            TransferAmountInput(
                input.from,
                input.to,
                input.amount
            )
        )
    }

}