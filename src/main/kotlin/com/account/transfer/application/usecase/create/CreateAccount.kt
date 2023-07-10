package com.account.transfer.application.usecase.create

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.springframework.stereotype.Component

@Component
class CreateAccount (
    private val accountPersistencePort: AccountPersistencePort,
) {

    fun execute(input: CreateAccountInput): CreateAccountOutput {
        accountPersistencePort.save(Account(input.accountId))

        return CreateAccountOutput(true)
    }

}