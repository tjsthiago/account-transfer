package com.account.transfer.infra.driver.messaging.input

data class CreditAmountInputMessage (
    val accountId: Long,
    val amount: Double
)