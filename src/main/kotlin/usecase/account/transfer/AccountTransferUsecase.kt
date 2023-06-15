package usecase.account.transfer

import entity.AccountTransferService
import ports.driven.repository.AccountRepository

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