package account.usecase

import entity.Account
import entity.AccountTransferService
import ports.driven.repository.InMemoryAccountRepository
import org.junit.jupiter.api.Test
import usecase.AccountTransferUseCaseInput
import usecase.AccountTransferUsecase

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