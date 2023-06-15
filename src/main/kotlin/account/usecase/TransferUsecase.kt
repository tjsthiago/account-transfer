package account.usecase

import account.entity.TransferService
import account.gateway.AccountGateway

class TransferUsecase(
    private val accountGateway: AccountGateway,
    private val transferService: TransferService
) {

    fun execute(input: TransferUseCaseInput): TransferUseCaseOutput{
        val from = this.accountGateway.get(input.from)
        val to = this.accountGateway.get(input.to)

        transferService.transfer(
            from,
            to,
            input.amount
        )

        accountGateway.update(from)
        accountGateway.update(to)

        return TransferUseCaseOutput(true)
    }

}