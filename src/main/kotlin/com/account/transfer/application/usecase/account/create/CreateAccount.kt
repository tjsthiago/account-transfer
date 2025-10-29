package com.account.transfer.application.usecase.account.create

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.domain.events.AccountCreatedEvent
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import com.account.transfer.domain.exceptions.DuplicateAccountException
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateAccount (
    private val accountPersistencePort: AccountPersistencePort,
    private val accountCreatedMessagePort: AccountCreatedMessagePort
) {

    fun execute(input: Input): Output {
        if(existsByAccountId(input)) {
            throw DuplicateAccountException("Account with ID ${input.accountId} already exists")
        }

        val accountToPersist = Account(input.accountId)

        accountPersistencePort.save(accountToPersist)
        publishAccountCreatedEvent(input)

        return Output(true)
    }

    private fun existsByAccountId(input: Input): Boolean = accountPersistencePort.existsByAccountId(input.accountId)

    private fun publishAccountCreatedEvent(input: Input) {
        accountCreatedMessagePort.send(
            AccountCreatedEvent(
                input.accountId,
                "Account ${input.accountId} created",
                Date()
            )
        )
    }

}

class Input (
    val accountId: Long
)

class Output (
    val success: Boolean
)