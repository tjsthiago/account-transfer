package com.account.transfer.infra.driver.rest

import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.infra.driver.rest.request.CreateAccountRequest
import com.account.transfer.infra.driver.rest.request.CreditAmountRequest
import com.account.transfer.infra.driver.rest.request.TransferAmountRequest
import com.account.transfer.infra.driver.rest.response.CreateAccountResponse
import com.account.transfer.infra.driver.rest.response.CreditAmountResponse
import com.account.transfer.infra.driver.rest.response.TransferAmountResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput
import com.account.transfer.application.usecase.ammount.transfer.Input as TransferAmountInput

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val createAccount: CreateAccount,
    private val creditAmount: CreditAmount,
    private val transferAmountBetweenAccounts: TransferAmountBetweenAccounts
) {

    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateAccountRequest): ResponseEntity<CreateAccountResponse> {
        return try {
            logger.info(
                "Received request to create account with accountId: ${request.accountId}"
            )

            val output = createAccount.execute(CreateAccountInput(request.accountId))

            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateAccountResponse(
                        output.success,
                        message = "Account created successfully with accountId: ${request.accountId}"
                    )
                )

        } catch (ex: Exception) {
            logger.error("Error on create account", ex)

            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CreateAccountResponse(
                    success = false,
                    message = ex.message
                )
            )
        }
    }

    @PostMapping("/transfer")
    fun transferAmount(@RequestBody request: TransferAmountRequest): ResponseEntity<TransferAmountResponse> {
        return try {
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

            ResponseEntity
                .status(HttpStatus.OK)
                .body(TransferAmountResponse(
                        output.success,
                        message = "Amount ${request.amount} transferred from account ${request.from} to account ${request.to}"
                    )
                )

        } catch (ex: Exception) {
            logger.error("Error on transfer amount", ex)

            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                TransferAmountResponse(
                    success = false,
                    message = ex.message
                )
            )
        }
    }

    @PatchMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    fun credit(
        @PathVariable accountId: Long,
        @RequestBody request: CreditAmountRequest
    ): ResponseEntity<CreditAmountResponse> {
        return try {
            logger.info(
                "Received request to credit amount ${request.amount} to account $accountId"
            )

            val output = creditAmount.execute(CreditAmountInput(accountId, request.amount))

            ResponseEntity
                .status(HttpStatus.OK)
                .body(CreditAmountResponse(
                    output.success,
                    message = "Account $accountId credited with amount ${request.amount}"
                    )
                )

        } catch (ex: Exception) {
            logger.error("Error on credit amount", ex)

            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CreditAmountResponse(
                    success = false,
                    message = ex.message
                )
            )
        }
    }

}