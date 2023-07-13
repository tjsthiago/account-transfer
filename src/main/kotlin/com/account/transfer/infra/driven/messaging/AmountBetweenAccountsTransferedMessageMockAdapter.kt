package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferedMessagePort
import com.account.transfer.domain.events.AmountBetweenAccountsTransferedEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("AmountBetweenAccountsTransferedMessageMockAdapter")
class AmountBetweenAccountsTransferedMessageMockAdapter : AmountBetweenAccountsTransferedMessagePort {
    override fun send(event: AmountBetweenAccountsTransferedEvent) {
        println(event)
    }
}