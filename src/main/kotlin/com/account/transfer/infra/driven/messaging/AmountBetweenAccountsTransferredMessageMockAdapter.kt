package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferredMessagePort
import com.account.transfer.domain.events.AmountBetweenAccountsTransferedEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("AmountBetweenAccountsTransferredMessageMockAdapter")
class AmountBetweenAccountsTransferredMessageMockAdapter : AmountBetweenAccountsTransferredMessagePort {
    override fun send(event: AmountBetweenAccountsTransferedEvent) {
        println(event)
    }
}