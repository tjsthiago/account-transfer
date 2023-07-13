package com.account.transfer.application.messaging

import com.account.transfer.domain.events.AccountCreatedEvent

interface AccountCreatedMessagePort {
    fun send(event: AccountCreatedEvent)
}