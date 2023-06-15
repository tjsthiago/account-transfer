package account.usecase

import account.entity.Account
import account.entity.TransferService
import account.gateway.InMemoryAccountGateway
import org.junit.jupiter.api.Test

class TransferUseCaseTest {

    @Test
    fun `should transfer amount between two accounts`() {
        val accountGateway = InMemoryAccountGateway()
        val transferService = TransferService()

        val transferUseCase = TransferUsecase(
            accountGateway,
            transferService
        )

        val from = Account("987654")
        from.credit(100.0)
        accountGateway.save(from)

        val to = Account("456123")
        accountGateway.save(to)

        val amount = 50.0

        val input = TransferUseCaseInput(
            from.getId(),
            to.getId(),
            amount
        )

        transferUseCase.execute(input)
    }
}