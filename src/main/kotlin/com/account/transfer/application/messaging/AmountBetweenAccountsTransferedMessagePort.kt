package com.account.transfer.application.messaging

import com.account.transfer.application.messaging.events.AmountBetweenAccountsTransferedEvent

interface AmountBetweenAccountsTransferedMessagePort {
    fun send(event: AmountBetweenAccountsTransferedEvent)
}