package com.account.transfer.application.usecase.ammount.transfer

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferredMessagePort
import com.account.transfer.domain.events.AmountBetweenAccountsTransferredEvent
import com.account.transfer.domain.entities.AccountAmountTransferService
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.springframework.stereotype.Component
import java.util.*

@Component
class TransferAmountBetweenAccounts(
    private val accountPersistencePort: AccountPersistencePort,
    private val amountBetweenAccountsTransferredMessagePort: AmountBetweenAccountsTransferredMessagePort
) {

    val accountAmountTransferService = AccountAmountTransferService()

    fun execute(input: Input): Output {
        val from = getAccountOrThrowExceptionIfNotFound(input.from)
        val to = getAccountOrThrowExceptionIfNotFound(input.to)

        transferAmount(from, to, input)

        updateAccount(from)
        updateAccount(to)

        publishTransferAmountBetweenAccountsEvent(input)

        return Output(true)
    }

    private fun updateAccount(account: Account) {
        accountPersistencePort.update(account)
    }

    private fun transferAmount(
        from: Account,
        to: Account,
        input: Input
    ) {
        accountAmountTransferService.transfer(
            from,
            to,
            input.amount
        )
    }

    private fun getAccountOrThrowExceptionIfNotFound(accountId: Long) =
        this.accountPersistencePort.findByAccountId(accountId)

    private fun publishTransferAmountBetweenAccountsEvent(input: Input) {
        amountBetweenAccountsTransferredMessagePort.send(
            AmountBetweenAccountsTransferredEvent(
                input.from,
                input.to,
                "${input.amount} transferred from ${input.from} to ${input.to} account",
                Date()
            )
        )
    }

}

class Input (
    val from: Long,
    val to: Long,
    val amount: Double
)

class Output(
    val success: Boolean
)