package com.account.transfer.domain.entities

class AccountAmountTransferService {

    fun transfer(from: Account, to: Account, amount: Double) {
        from.debit(amount)
        to.credit(amount)
    }

}