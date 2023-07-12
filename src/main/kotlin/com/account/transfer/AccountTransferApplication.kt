package com.account.transfer

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.infra.driven.repository.AccountPersistenceAdapter
import com.account.transfer.infra.driven.repository.AccountRepository
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class AccountTransferApplication{

	@Autowired
	private lateinit var accountRepository: AccountRepository

	@Bean
	fun getAccountPersistencePort(): AccountPersistencePort {
		return AccountPersistenceAdapter(accountRepository)
	}

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

	@Bean
	fun converter(): MessageConverter {
		val mapper = ObjectMapper()
			.setSerializationInclusion(JsonInclude.Include.ALWAYS)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

		return Jackson2JsonMessageConverter(mapper)
	}

	@Bean
	fun amqpTemplate(connectionFactory: ConnectionFactory?): AmqpTemplate {
		val rabbitTemplate = RabbitTemplate(connectionFactory!!)
		rabbitTemplate.messageConverter = converter()
		return rabbitTemplate
	}
}

fun main(args: Array<String>) {
	runApplication<AccountTransferApplication>(*args)
}
