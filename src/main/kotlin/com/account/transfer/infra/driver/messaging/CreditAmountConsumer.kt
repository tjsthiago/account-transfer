package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.credit.CreditAmountInput
import com.account.transfer.infra.driver.messaging.input.CreditAmountInputMessage
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CreditAmountConsumer (
    private val creditAmount: CreditAmount
) {

    @RabbitListener(queues = ["\${queue.credit.amount}"], ackMode = "AUTO")
    fun creditAmount(input: CreditAmountInputMessage) {
        creditAmount.execute(
            CreditAmountInput(
                input.accountId,
                input.amount
            )
        )
    }

}