package application.usecase

import domain.entities.Account
import domain.entities.AccountTransferService
import infra.driven.repository.InMemoryAccountRepository
import org.junit.jupiter.api.Test

class AccountTransferUseCaseTest {

    @Test
    fun `should transfer amount between two accounts`() {
        val accountRepository = InMemoryAccountRepository()
        val accountTransferService = AccountTransferService()

        val transferUseCase = AccountTransferUsecase(
            accountRepository,
            accountTransferService
        )

        val from = Account("987654")
        from.credit(100.0)
        accountRepository.save(from)

        val to = Account("456123")
        accountRepository.save(to)

        val amount = 50.0

        val input = AccountTransferUseCaseInput(
            from.getId(),
            to.getId(),
            amount
        )

        transferUseCase.execute(input)
    }
}