package com.account.transfer.infra.driver.messaging

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.infra.driver.messaging.input.CreateAccountInputMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput

@Component
class CreateAccountConsumer(
    private val createAccount: CreateAccount
) {
    private val logger: Logger = LoggerFactory.getLogger(CreateAccountConsumer::class.java)

    @RabbitListener(queues = ["\${queue.create.account}"], ackMode = "AUTO")
    fun createAccount(input: CreateAccountInputMessage) {
        logger.info(
            "Received request to create account with accountId: ${input.accountId}"
        )

        try {
            createAccount.execute(CreateAccountInput(input.accountId))
        } catch (e: Exception) {
            logger.error("Error processing create account request: ${e.message}", e)
        }
    }

}