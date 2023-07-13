package com.account.transfer.infra.driven.messaging

import com.account.transfer.application.messaging.AmountCreditedMessagePort
import com.account.transfer.application.messaging.events.AmountCreditedEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("AmountCreditedMessageMockAdapter")
class AmountCreditedMessageMockAdapter : AmountCreditedMessagePort {
    override fun send(event: AmountCreditedEvent) {
        println(event)
    }
}