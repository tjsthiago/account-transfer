package com.account.transfer.domain.entities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountTransferServiceTest {

    private val transferService = AccountTransferService()

    @Test
    fun `should transfer amount between two accounts`() {
        val from = Account(987654L)
        from.credit(100.0)

        val to = Account(456123L)

        val amount = 50.0

        transferService.transfer(from, to, amount)

        assertEquals(from.balance, 50.0)
        assertEquals(to.balance, 50.0)

    }
}