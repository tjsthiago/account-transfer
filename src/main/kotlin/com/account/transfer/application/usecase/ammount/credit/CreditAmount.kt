package com.account.transfer.application.usecase.ammount.credit

import com.account.transfer.application.messaging.AmountCreditedMessagePort
import com.account.transfer.domain.events.AmountCreditedEvent
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreditAmount(
    private val accountPersistencePort: AccountPersistencePort,
    private val amountCreditedMessagePort: AmountCreditedMessagePort
) {

    fun execute(input: CreditAmountInput): CreditAmountOutput {
        val account = getAccountToCreditOrThrowExceptionIfNotFound(input)
        account.credit(input.amount)
        updateCreditedAccount(account)
        publicAccountCreditedEvent(input)

        return CreditAmountOutput(true)
    }

    private fun publicAccountCreditedEvent(input: CreditAmountInput) {
        amountCreditedMessagePort.send(
            AmountCreditedEvent(
                input.accountId,
                "${input.amount} amount cretited to account ${input.accountId}",
                Date()
            )
        )
    }

    private fun updateCreditedAccount(account: Account) {
        accountPersistencePort.update(account)
    }

    private fun getAccountToCreditOrThrowExceptionIfNotFound(input: CreditAmountInput) =
        accountPersistencePort.findByAccountId(
            input.accountId
        )

}