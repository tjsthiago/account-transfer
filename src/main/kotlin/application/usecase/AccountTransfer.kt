package application.usecase

import domain.entities.AccountTransferService
import application.repository.AccountRepository

class AccountTransfer(
    private val accountGateway: AccountRepository,
    private val accountTransferService: AccountTransferService
) {

    fun execute(input: AccountTransferInput): AccountTransferOutput {
        val from = this.accountGateway.findById(input.from)
        val to = this.accountGateway.findById(input.to)

        accountTransferService.transfer(
            from,
            to,
            input.amount
        )

        accountGateway.update(from)
        accountGateway.update(to)

        return AccountTransferOutput(true)
    }

}