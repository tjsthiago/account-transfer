package com.account.transfer.application.messaging

import com.account.transfer.domain.events.AmountBetweenAccountsTransferedEvent

interface AmountBetweenAccountsTransferredMessagePort {
    fun send(event: AmountBetweenAccountsTransferedEvent)
}