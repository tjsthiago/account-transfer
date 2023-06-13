package account

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountTest {

	@Test
	fun `should create a new account`() {
		val account = Account("9875654")

		assertEquals(account.balance, 0)
	}

	@Test
	fun `should crediti an account`() {
		val account = Account("9875654")

		account.credit(100)

		assertEquals(account.balance, 100)
	}

	@Test
	fun `should debit an account`() {
		val account = Account("9875654")

		account.credit(100)
		account.debit(50)

		assertEquals(account.balance, 50)
	}

}
