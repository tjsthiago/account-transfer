package com.domain.entities

import com.infra.driven.repository.AccountEntity

class Account(
    private val id: Long,
) {
    fun getId() = id

    var balance: Double = 0.0
        private set

    fun credit(amount: Double) {
        this.balance += amount
    }

    fun debit(amount: Double) {
        this.balance -= amount
    }

}
