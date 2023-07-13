package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.events.AccountCreatedEvent
import com.account.transfer.application.messaging.AccountCreatedMessagePort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("AccountCreatedMessageMockAdapter")
class AccountCreatedMessageMockAdapter: AccountCreatedMessagePort {
    override fun send(event: AccountCreatedEvent) {
        println(event)
    }
}