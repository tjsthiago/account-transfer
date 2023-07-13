package com.account.transfer.domain.events

import java.util.*

data class AmountBetweenAccountsTransferedEvent (
    val originAccount: Long,
    val targetAccount: Long,
    val message: String,
    val createdAt: Date
)