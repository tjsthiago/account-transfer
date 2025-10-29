package com.account.transfer.infra.driver.messaging.account.exception

import com.account.transfer.domain.exceptions.DuplicateAccountException
import com.account.transfer.domain.exceptions.InsufficientBalanceException
import com.account.transfer.infra.driven.repository.AccountNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.messaging.Message as SpringMessage
import org.springframework.stereotype.Component
import com.rabbitmq.client.Channel

@Component
class AccountMessagingExceptionHandler : RabbitListenerErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(AccountMessagingExceptionHandler::class.java)

    override fun handleError(amqpMessage: Message, springMessage: SpringMessage<*>?, ex: ListenerExecutionFailedException): Any? {
        val channel = springMessage?.headers?.get("channel") as? Channel
        val deliveryTag = amqpMessage.messageProperties.deliveryTag
        val cause = ex.cause

        when (cause) {
            is DuplicateAccountException -> {
                channel?.basicAck(deliveryTag, false)
                logger.error("DuplicateAccountException intercepted and acked: ${cause.message}", cause)
            }
            is AccountNotFoundException -> {
                channel?.basicAck(deliveryTag, false)
                logger.error("AccountNotFoundException intercepted and acked: ${cause.message}", cause)
            }
            is InsufficientBalanceException -> {
                channel?.basicAck(deliveryTag, false)
                logger.error("InsufficientBalanceException intercepted and acked: ${cause.message}", cause)
            }
            else -> {
                logger.error("Unhandled exception: ${cause?.message}", cause)
            }
        }
        return null
    }
}