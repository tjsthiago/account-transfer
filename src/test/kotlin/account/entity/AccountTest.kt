package account.entity

import entity.Account
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountTest {

	@Test
	fun `should create a new account`() {
		val account = Account("9875654")

		assertEquals(account.balance, 0.0)
	}

	@Test
	fun `should credit an account`() {
		val account = Account("9875654")
		account.credit(100.0)

		assertEquals(account.balance, 100.0)
	}

	@Test
	fun `should debit an account`() {
		val account = Account("9875654")
		account.credit(100.0)
		account.debit(50.0)

		assertEquals(account.balance, 50.0)
	}

}
