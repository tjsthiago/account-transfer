package com.account.transfer.application.messaging

import com.account.transfer.application.messaging.events.AccountCreatedEvent

interface AccountCreatedMessagePort {
    fun send(event: AccountCreatedEvent)
}