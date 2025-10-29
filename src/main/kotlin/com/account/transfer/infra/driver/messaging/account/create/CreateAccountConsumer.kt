package com.account.transfer.infra.driver.messaging.account.create

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.Input
import com.rabbitmq.client.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CreateAccountConsumer(
    private val createAccount: CreateAccount
) {
    private val logger: Logger = LoggerFactory.getLogger(CreateAccountConsumer::class.java)

    @RabbitListener(queues = ["\${queue.create.account}"], ackMode = "AUTO", errorHandler = "accountMessagingExceptionHandler")
    fun createAccount(input: CreateAccountInputMessage, message: Message, channel: Channel) {
        logger.info(
            "Received request to create account with accountId: ${input.accountId}"
        )

        createAccount.execute(Input(input.accountId))

        logger.info(
            "Account with accountId: ${input.accountId} created successfully"
        )
    }

}