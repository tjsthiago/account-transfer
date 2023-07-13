package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CreateAccountConsumer(
    private val createAccount: CreateAccount
) {

    @RabbitListener(queues = ["\${queue.create.account}"], ackMode = "AUTO")
    fun creetaAccount(input: CreateAccountInputMessage) {
        createAccount.execute(CreateAccountInput(input.accountId))
    }

}

class CreateAccountInputMessage (
    @JsonProperty("accountId")
    val accountId: Long
)