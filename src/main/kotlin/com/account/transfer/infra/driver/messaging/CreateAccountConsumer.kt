package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.create.CreateAccount
import com.account.transfer.application.usecase.create.CreateAccountInput
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