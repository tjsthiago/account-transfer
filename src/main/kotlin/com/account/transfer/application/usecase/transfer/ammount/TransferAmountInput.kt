package com.account.transfer.application.usecase.transfer.ammount

data class TransferAmountInput (
    val from: Long,
    val to: Long,
    val amount: Double
)