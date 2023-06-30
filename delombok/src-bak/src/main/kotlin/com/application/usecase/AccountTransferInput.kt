package com.application.usecase

data class AccountTransferInput (
    val from: Long,
    val to: Long,
    val amount: Double
)