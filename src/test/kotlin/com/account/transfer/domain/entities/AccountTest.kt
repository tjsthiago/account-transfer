package com.account.transfer.domain.entities

import com.account.transfer.domain.exceptions.InsufficientBalanceException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountTest {

	@Test
	fun `should create a new account`() {
		val account = Account(9875654L)

		assertEquals(account.balance, 0.0)
	}

	@Test
	fun `should credit an account`() {
		val account = Account(9875654L)
		account.credit(100.0)

		assertEquals(account.balance, 100.0)
	}

	@Test
	fun `should debit an account`() {
		val account = Account(9875654L)
		account.credit(100.0)
		account.debit(50.0)

		assertEquals(account.balance, 50.0)
	}

    @Test
    fun `should throw exception when try to debit a value higher than the account amount`() {
        val account = Account(987654L)
        val initialAmount = 100.0
        val withdrawal = 200.0

        account.credit(initialAmount)

        val thrown: InsufficientBalanceException = Assertions.assertThrows(
            InsufficientBalanceException::class.java,
            { account.debit(withdrawal) },
            "InsufficientBalanceException was expected"
        )

        assertEquals("The account [987654] has no sufficient balance to transfer.", thrown.message)
    }

}
