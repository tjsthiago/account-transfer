package com.account.transfer.application.usecase.ammount.transfer

data class TransferAmountInput (
    val from: Long,
    val to: Long,
    val amount: Double
)