package com.account.transfer.application.usecase.ammount.credit

data class CreditAmountInput (
    val accountId: Long,
    val amount: Double
)