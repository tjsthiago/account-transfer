package com.account.transfer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class AccountTransferApplication {
}

fun main(args: Array<String>) {
	runApplication<AccountTransferApplication>(*args)
}
