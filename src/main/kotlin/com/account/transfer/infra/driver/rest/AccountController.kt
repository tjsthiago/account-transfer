package com.account.transfer.infra.driver.rest

import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.credit.CreditAmountInput
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountInput
import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.CreateAccountInput
import com.account.transfer.infra.driver.rest.request.CreateAccountRequest
import com.account.transfer.infra.driver.rest.request.CreditAmountRequest
import com.account.transfer.infra.driver.rest.request.TransferAmountRequest
import com.account.transfer.infra.driver.rest.response.CreateAccountResponse
import com.account.transfer.infra.driver.rest.response.CreditAmountResponse
import com.account.transfer.infra.driver.rest.response.TransferAmountResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController (
    private val createAccount: CreateAccount,
    private val creditAmount: CreditAmount,
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateAccountRequest): CreateAccountResponse {
        val output = createAccount.execute(CreateAccountInput(request.accountId))

        return CreateAccountResponse(
            output.success
        )
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    fun transferAmount(@RequestBody request: TransferAmountRequest): TransferAmountResponse {
        val output = transferAmountBetweenAccounts.execute(
            TransferAmountInput(
                request.from,
                request.to,
                request.amount
            )
        )

        return TransferAmountResponse(
            output.success
        )
    }

    @PatchMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    fun credit(@PathVariable accountId: Long ,@RequestBody request: CreditAmountRequest): CreditAmountResponse {
        val output = creditAmount.execute(CreditAmountInput(accountId, request.amount))

        return CreditAmountResponse(
            output.success
        )
    }

}