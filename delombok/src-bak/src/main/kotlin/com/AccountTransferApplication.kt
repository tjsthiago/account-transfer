package com

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccountTransferApplication

fun main(args: Array<String>) {
	runApplication<AccountTransferApplication>(*args)
}
