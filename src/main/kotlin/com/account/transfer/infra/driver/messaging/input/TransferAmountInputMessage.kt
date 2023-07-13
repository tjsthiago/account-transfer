package com.account.transfer.infra.driver.messaging.input

data class TransferAmountInputMessage (
    val from: Long,
    val to: Long,
    val amount: Double
)