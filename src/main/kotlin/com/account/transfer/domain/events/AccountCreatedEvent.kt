package com.account.transfer.domain.events

import java.util.Date

data class AccountCreatedEvent (
    val accountId: Long,
    val message: String,
    val createdAt: Date
)
