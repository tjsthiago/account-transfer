package com.account.transfer.application.messaging

import com.account.transfer.domain.events.AmountCreditedEvent

interface AmountCreditedMessagePort {
    fun send(event: AmountCreditedEvent)
}