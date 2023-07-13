package com.account.transfer.application.usecase.create

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

        accountPersistencePort.save(Account(input.accountId))

        accountCreatedMessagePort.send(
            AccountCreatedEvent(
                input.accountId,
                "Account ${input.accountId} created",
                Date()
            )
        )

        return CreateAccountOutput(true)
    }

}