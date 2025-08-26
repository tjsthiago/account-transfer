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

    fun execute(input: Input): Output {
        val account = getAccountToCreditOrThrowExceptionIfNotFound(input)
        account.credit(input.amount)
        updateCreditedAccount(account)
        publishAccountCreditedEvent(input)

        return Output(true)
    }

    private fun publishAccountCreditedEvent(input: Input) {
        amountCreditedMessagePort.send(
            AmountCreditedEvent(
                input.accountId,
                "${input.amount} amount credited to account ${input.accountId}",
                Date()
            )
        )
    }

    private fun updateCreditedAccount(account: Account) {
        accountPersistencePort.update(account)
    }

    private fun getAccountToCreditOrThrowExceptionIfNotFound(input: Input) =
        accountPersistencePort.findByAccountId(
            input.accountId
        )

}

class Input (
    val accountId: Long,
    val amount: Double
)

class Output (
    val success: Boolean
)