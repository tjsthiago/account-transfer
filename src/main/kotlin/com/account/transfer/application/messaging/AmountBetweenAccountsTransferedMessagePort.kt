package com.account.transfer.application.messaging

import com.account.transfer.domain.events.AmountBetweenAccountsTransferedEvent

interface AmountBetweenAccountsTransferedMessagePort {
    fun send(event: AmountBetweenAccountsTransferedEvent)
}