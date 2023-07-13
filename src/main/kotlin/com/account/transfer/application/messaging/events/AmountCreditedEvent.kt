package com.account.transfer.application.messaging.events

import java.util.*

data class AmountCreditedEvent (
    val accountId: Long,
    val message: String,
    val createdAt: Date
)