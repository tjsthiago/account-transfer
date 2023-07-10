package com.account.transfer.application.usecase.transfer.ammount

import com.account.transfer.domain.entities.AccountAmountTransferService
import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.stereotype.Component

@Component
class TransferAmountBetweenAccounts(
    private val accountPersistencePort: AccountPersistencePort
) {

    val accountAmountTransferService = AccountAmountTransferService()

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