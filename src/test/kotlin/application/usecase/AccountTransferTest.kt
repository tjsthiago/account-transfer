package application.usecase

import domain.entities.Account
import domain.entities.AccountTransferService
import infra.driven.repository.InMemoryAccountRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AccountTransferTest {

    @Test
    fun `should transfer amount between two accounts`() {
        val accountRepository = InMemoryAccountRepository()
        val accountTransferService = AccountTransferService()

        val transferUseCase = AccountTransfer(
            accountRepository,
            accountTransferService
        )

        val from = Account("987654")
        from.credit(100.0)
        accountRepository.save(from)

        val to = Account("456123")
        accountRepository.save(to)

        val amount = 50.0

        val input = AccountTransferInput(
            from.getId(),
            to.getId(),
            amount
        )

        val result = transferUseCase.execute(input)

        assertTrue(result.success)
    }
}