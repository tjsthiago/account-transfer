package com.account.transfer.application.messaging

import com.account.transfer.domain.events.AmountBetweenAccountsTransferredEvent

interface AmountBetweenAccountsTransferredMessagePort {
    fun send(event: AmountBetweenAccountsTransferredEvent)
}