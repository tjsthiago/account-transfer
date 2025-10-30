package com.account.transfer.infra.driver.rest.account

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.info.GetAccountInfo
import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.infra.driver.rest.account.request.CreateAccountRequest
import com.account.transfer.infra.driver.rest.account.request.CreditAmountRequest
import com.account.transfer.infra.driver.rest.account.request.TransferAmountRequest
import com.account.transfer.infra.driver.rest.account.response.CreateAccountResponse
import com.account.transfer.infra.driver.rest.account.response.CreditAmountResponse
import com.account.transfer.infra.driver.rest.account.response.TransferAmountResponse
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.account.transfer.application.usecase.account.info.Input as GetAccountInfoInput
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput
import com.account.transfer.application.usecase.ammount.transfer.Input as TransferAmountInput

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val createAccount: CreateAccount,
    private val creditAmount: CreditAmount,
    private val getAccountInfo: GetAccountInfo,
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {

    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    fun getAccountInfo(@PathVariable accountId: Long): ResponseEntity<Any> {
        logger.info("Received request to get info for accountId: $accountId")
        val output = getAccountInfo.execute(GetAccountInfoInput(accountId))
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(output)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateAccountRequest): ResponseEntity<CreateAccountResponse> {
        logger.info("Received request to create account with accountId: ${request.accountId}")
        val output = createAccount.execute(CreateAccountInput(request.accountId!!))
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                CreateAccountResponse(
                    output.success,
                    message = "Account created successfully with accountId: ${request.accountId}"
                )
            )
    }

    @PostMapping("/transfer")
    fun transferAmount(@RequestBody request: TransferAmountRequest): ResponseEntity<TransferAmountResponse> {
        logger.info(
            "Received request to transfer amount ${request.amount} from account ${request.from} to account ${request.to}"
        )
        val output = transferAmountBetweenAccounts.execute(
            TransferAmountInput(
                request.from,
                request.to,
                request.amount
            )
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                TransferAmountResponse(
                    output.success,
                    message = "Amount ${request.amount} transferred from account ${request.from} to account ${request.to}"
                )
            )
    }

    @PatchMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    fun credit(
        @PathVariable accountId: Long,
        @RequestBody request: CreditAmountRequest
    ): ResponseEntity<CreditAmountResponse> {
        logger.info(
            "Received request to credit amount ${request.amount} to account $accountId"
        )
        val output = creditAmount.execute(CreditAmountInput(accountId, request.amount))
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CreditAmountResponse(
                output.success,
                message = "Account $accountId credited with amount ${request.amount}"
            ))
    }

}