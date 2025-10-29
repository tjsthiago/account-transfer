package com.account.transfer.infra.driver.messaging.account.create

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.Input
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

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
            createAccount.execute(Input(input.accountId))
        } catch (e: Exception) {
            logger.error("Error processing create account request: ${e.message}", e)
        }
    }

}