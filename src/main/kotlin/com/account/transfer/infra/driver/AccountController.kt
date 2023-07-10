package com.account.transfer.infra.driver

import com.account.transfer.application.usecase.create.CreateAccount
import com.account.transfer.application.usecase.create.CreateAccountInput
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController (
    private val createAccount: CreateAccount
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateAccountRequest): CreateAccountResponse {
        val output = createAccount.execute(CreateAccountInput(request.accountId))

        return CreateAccountResponse(
            output.success
        )
    }
}