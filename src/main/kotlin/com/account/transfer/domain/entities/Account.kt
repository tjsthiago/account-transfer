package com.account.transfer.domain.entities

class Account(
    private val accountId: Long,
) {
    fun getAccountId() = accountId

    var balance: Double = 0.0
        private set

    fun credit(amount: Double) {
        this.balance += amount
    }

    fun debit(amount: Double) {
        this.balance -= amount
    }

}
