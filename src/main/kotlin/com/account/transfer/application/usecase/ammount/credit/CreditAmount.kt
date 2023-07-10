package com.account.transfer.application.usecase.ammount.credit

import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.stereotype.Component

@Component
class CreditAmount(
    private val accountPersistencePort: AccountPersistencePort
) {

    fun execute(input: CreditAmountInput): CreditAmountOutput {
        val account = accountPersistencePort.findByAccountId(
            input.accountId
        )

        account.credit(input.amount)

        accountPersistencePort.update(account)

        return CreditAmountOutput(true)
    }

}