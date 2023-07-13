package com.account.transfer.application.messaging

import com.account.transfer.application.messaging.events.AmountCreditedEvent

interface AmountCreditedMessagePort {
    fun send(event: AmountCreditedEvent)
}