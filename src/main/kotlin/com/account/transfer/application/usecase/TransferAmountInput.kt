package com.account.transfer.application.usecase

data class TransferAmountInput (
    val from: Long,
    val to: Long,
    val amount: Double
)