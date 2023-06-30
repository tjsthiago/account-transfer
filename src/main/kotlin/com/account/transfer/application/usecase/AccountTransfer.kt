package com.account.transfer.application.usecase

import com.account.transfer.domain.entities.AccountTransferService
import com.account.transfer.application.repository.AccountPersistencePort

class AccountTransfer(
    private val accountPersistencePort: AccountPersistencePort,
    private val accountTransferService: AccountTransferService
) {

    fun execute(input: AccountTransferInput): AccountTransferOutput {
        val from = this.accountPersistencePort.findByAccountId(input.from)
        val to = this.accountPersistencePort.findByAccountId(input.to)

        accountTransferService.transfer(
            from,
            to,
            input.amount
        )

        accountPersistencePort.update(from)
        accountPersistencePort.update(to)

        return AccountTransferOutput(true)
    }

}