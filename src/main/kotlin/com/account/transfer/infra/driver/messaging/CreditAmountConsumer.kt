package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput
import com.fasterxml.jackson.annotation.JsonProperty
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

class CreditAmountInputMessage (
    @JsonProperty("accountId")
    val accountId: Long,

    @JsonProperty("amount")
    val amount: Double
)