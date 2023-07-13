package com.account.transfer.application.messaging.events

import java.util.*

data class AmountBetweenAccountsTransferedEvent (
    val originAccount: Long,
    val targetAccount: Long,
    val message: String,
    val createdAt: Date
)