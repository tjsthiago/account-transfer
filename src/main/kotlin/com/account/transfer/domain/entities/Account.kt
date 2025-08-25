package com.account.transfer.domain.entities

import com.account.transfer.domain.exceptions.InsufficientBalanceException

class Account(
    private val accountId: Long,
) {
    fun getAccountId() = accountId

    var balance: Double = 0.0
        private set

    fun credit(amount: Double) {
        this.balance += amount
    }

    fun debit(withdrawal: Double) {
        if(withdrawal > this.balance) {
            throw InsufficientBalanceException(
                "The account [$accountId] has no sufficient balance to transfer."
            )
        }

        this.balance -= withdrawal
    }

}
