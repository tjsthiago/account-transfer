package com.account.transfer.application.usecase.account.info

import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.stereotype.Component

@Component
class GetAccountInfo(
    private val accountPersistencePort: AccountPersistencePort,
) {

    fun execute(input: Input): Output {
        val account = accountPersistencePort.findByAccountId(input.accountId)

        return Output(
            accountId = account.getAccountId(),
            balance = account.balance
        )
    }

}

class Input (
    val accountId: Long
)

class Output (
    val accountId: Long,
    val balance: Double
)