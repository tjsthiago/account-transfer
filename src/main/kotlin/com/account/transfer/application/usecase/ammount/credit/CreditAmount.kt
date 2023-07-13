package com.account.transfer.application.usecase.ammount.credit

import com.account.transfer.application.messaging.AmountCreditedMessagePort
import com.account.transfer.domain.events.AmountCreditedEvent
import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreditAmount(
    private val accountPersistencePort: AccountPersistencePort,
    private val amountCreditedMessagePort: AmountCreditedMessagePort
) {

    fun execute(input: CreditAmountInput): CreditAmountOutput {
        val account = accountPersistencePort.findByAccountId(
            input.accountId
        )

        account.credit(input.amount)

        accountPersistencePort.update(account)

        amountCreditedMessagePort.send(
            AmountCreditedEvent(
                input.accountId,
                "${input.amount} amount cretited to account ${input.accountId}",
                Date()
            )
        )

        return CreditAmountOutput(true)
    }

}