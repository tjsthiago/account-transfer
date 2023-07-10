package com.account.transfer

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.infra.driven.repository.AccountPersistenceAdapter
import com.account.transfer.infra.driven.repository.AccountRepository
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
}

fun main(args: Array<String>) {
	runApplication<AccountTransferApplication>(*args)
}
