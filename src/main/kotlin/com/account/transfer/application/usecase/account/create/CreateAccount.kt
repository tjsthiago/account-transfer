package com.account.transfer.application.usecase.account.create

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.domain.events.AccountCreatedEvent
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateAccount (
    private val accountPersistencePort: AccountPersistencePort,
    private val accountCreatedMessagePort: AccountCreatedMessagePort
) {

    fun execute(input: CreateAccountInput): CreateAccountOutput {
        val account = Account(input.accountId)

        persistCreatedAccount(account)
        publicAccountCreatedEvent(input)

        return CreateAccountOutput(true)
    }

    private fun persistCreatedAccount(account: Account) {
        accountPersistencePort.save(account)
    }

    private fun publicAccountCreatedEvent(input: CreateAccountInput) {
        accountCreatedMessagePort.send(
            AccountCreatedEvent(
                input.accountId,
                "Account ${input.accountId} created",
                Date()
            )
        )
    }

}