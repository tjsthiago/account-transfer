package com.account.transfer.domain.entities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountAmountTransferServiceTest {

    private val transferService = AccountAmountTransferService()

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

    @Test
    fun `should throw InsufficientBalanceException if origin account does not have enough balance`() {
        val from = Account(987654L)
        val to = Account(456123L)

        val amount = 50.0

        val thrown: InsufficientBalanceException = Assertions.assertThrows(
            InsufficientBalanceException::class.java,
            { transferService.transfer(from, to, amount) },
            "InsufficientBalanceException was expected"
        )

        assertEquals("The account [987654] has no sufficient balance to transfer.", thrown.message)
    }

}