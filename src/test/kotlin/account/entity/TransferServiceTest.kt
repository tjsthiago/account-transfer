package account.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransferServiceTest {

    private val transferService = TransferService()

    @Test
    fun `should transfer amount between two accounts`() {
        val from = Account("987654")
        from.credit(100.0)

        val to = Account("456123")

        val amount = 50.0

       transferService.transfer(from, to, amount)

        assertEquals(from.balance, 50.0)
        assertEquals(to.balance, 50.0)

    }
}