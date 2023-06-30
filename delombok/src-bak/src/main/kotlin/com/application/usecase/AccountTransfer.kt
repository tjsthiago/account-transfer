package com.application.usecase

import com.domain.entities.AccountTransferService
import com.application.repository.AccountPersistencePort

class AccountTransfer(
    private val accountPersistencePort: AccountPersistencePort,
    private val accountTransferService: AccountTransferService
) {

    fun execute(input: AccountTransferInput): AccountTransferOutput {
        val from = this.accountPersistencePort.findById(input.from)
        val to = this.accountPersistencePort.findById(input.to)

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