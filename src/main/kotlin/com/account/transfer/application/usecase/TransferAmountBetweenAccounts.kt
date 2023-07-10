package com.account.transfer.application.usecase

import com.account.transfer.domain.entities.AccountAmountTransferService
import com.account.transfer.application.repository.AccountPersistencePort

class TransferAmountBetweenAccounts(
    private val accountPersistencePort: AccountPersistencePort,
    private val accountAmountTransferService: AccountAmountTransferService
) {

    fun execute(input: TransferAmountInput): TransferAmountOutput {
        val from = this.accountPersistencePort.findByAccountId(input.from)
        val to = this.accountPersistencePort.findByAccountId(input.to)

        accountAmountTransferService.transfer(
            from,
            to,
            input.amount
        )

        accountPersistencePort.update(from)
        accountPersistencePort.update(to)

        return TransferAmountOutput(true)
    }

}