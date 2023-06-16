package application.usecase

import domain.entity.AccountTransferService
import ports.driven.AccountRepository

class AccountTransferUsecase(
    private val accountGateway: AccountRepository,
    private val accountTransferService: AccountTransferService
) {

    fun execute(input: AccountTransferUseCaseInput): AccountTransferUseCaseOutput {
        val from = this.accountGateway.get(input.from)
        val to = this.accountGateway.get(input.to)

        accountTransferService.transfer(
            from,
            to,
            input.amount
        )

        accountGateway.update(from)
        accountGateway.update(to)

        return AccountTransferUseCaseOutput(true)
    }

}