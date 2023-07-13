package com.account.transfer.application.usecase.ammount.transfer

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferedMessagePort
import com.account.transfer.application.messaging.events.AmountBetweenAccountsTransferedEvent
import com.account.transfer.domain.entities.AccountAmountTransferService
import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class TransferAmountBetweenAccounts(
    private val accountPersistencePort: AccountPersistencePort,
    private val amountBetweenAccountsTransferedMessagePort: AmountBetweenAccountsTransferedMessagePort
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

        amountBetweenAccountsTransferedMessagePort.send(
            AmountBetweenAccountsTransferedEvent(
                input.from,
                input.to,
                "${input.amount} transfered from ${input.from} to ${input.to} account",
                Date()
            )
        )

        return TransferAmountOutput(true)
    }

}