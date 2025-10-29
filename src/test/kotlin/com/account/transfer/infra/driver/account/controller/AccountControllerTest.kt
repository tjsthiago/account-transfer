package com.account.transfer.infra.driver.account.controller

import com.account.transfer.infra.driver.rest.account.request.CreateAccountRequest
import com.account.transfer.infra.driver.rest.account.request.TransferAmountRequest
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.*
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
){

    val baseUrl = "/api/accounts"

    @Test
    fun `should create a new account`() {
        val request = CreateAccountRequest(Random.nextLong(0, 99999))

        createAccount(request)
    }

    @Test
    fun `should throw DuplicateAccountException when try to create an account with duplicated accountId`() {
        val accountId = Random.nextLong(0, 99999)
        val request = CreateAccountRequest(accountId)

        createAccount(request)

        mockMvc
            .post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }
            .andDo { print() }
            .andExpect {
                status { isConflict() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("false") }
                jsonPath("$.message") { value("Account with ID $accountId already exists") }
            }

    }

    @Test
    fun `should credit an account`() {
        val accountId = Random.nextLong(0, 99999)
        createAccount(CreateAccountRequest(accountId))

        val amountToCredit = 50.0
        val creditAmountInput = CreditAmountInput(accountId, amountToCredit)

        credit(accountId, creditAmountInput)
    }

    @Test
    fun `should transfer amount between two accounts`() {
        val originAccountId = Random.nextLong(0, 99999)
        val originAccountRequest = CreateAccountRequest(originAccountId)

        val amountToTransfer = 50.0

        val amountToCreditInOriginAccount = 100.0
        val creditAmountInput = CreditAmountInput(originAccountId, amountToCreditInOriginAccount)

        val targetAccountId = Random.nextLong(0, 99999)
        val targetAccountRequest = CreateAccountRequest(targetAccountId)

        createAccount(originAccountRequest)
        credit(originAccountId, creditAmountInput)

        createAccount(targetAccountRequest)

        val transferAmountRequest = TransferAmountRequest(
            originAccountId,
            targetAccountId,
            amountToTransfer
        )

        val expectedMessage = "Amount ${transferAmountRequest.amount} transferred from account ${transferAmountRequest.from} to account ${transferAmountRequest.to}"

        mockMvc
            .post("$baseUrl/transfer") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(transferAmountRequest)
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("true") }
                jsonPath("$.message") { value(expectedMessage) }
            }
    }

    @Test
    fun `should throw InsufficientBalanceException when try to transfer amount greater than the origin account balance`() {
        val originAccountId = Random.nextLong(0, 99999)
        val originAccountRequest = CreateAccountRequest(originAccountId)

        val amountToTransfer = 150.0

        val amountToCreditInOriginAccount = 100.0
        val creditAmountInput = CreditAmountInput(originAccountId, amountToCreditInOriginAccount)

        val targetAccountId = Random.nextLong(0, 99999)
        val targetAccountRequest = CreateAccountRequest(targetAccountId)

        createAccount(originAccountRequest)
        credit(originAccountId, creditAmountInput)

        createAccount(targetAccountRequest)

        val transferAmountRequest = TransferAmountRequest(
            originAccountId,
            targetAccountId,
            amountToTransfer
        )

        mockMvc
            .post("$baseUrl/transfer") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(transferAmountRequest)
            }
            .andDo { print() }
            .andExpect {
                status { isPreconditionRequired() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("false") }
                jsonPath("$.message") { value("The account [$originAccountId] has no sufficient balance to transfer.") }
            }
    }

    @Test
    fun `Should throw AccountNotFoundException when try to credit an non existing account`() {
        val nonExistingAccountId = Random.nextLong(0, 99999)

        val amountToCredit = 50.0
        val creditAmountInput = CreditAmountInput(nonExistingAccountId, amountToCredit)

        mockMvc
            .patch("$baseUrl/$nonExistingAccountId") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(creditAmountInput)
            }
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("false") }
                jsonPath("$.message") { value("Account with id [$nonExistingAccountId] not found") }
            }
    }

    private fun createAccount(request: CreateAccountRequest) {
        val expectedMessage = "Account created successfully with accountId: ${request.accountId}"

        mockMvc
            .post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("true") }
                jsonPath("$.message") { value(expectedMessage) }
            }
    }

    private fun credit(
        accountId: Long,
        creditAmountInput: CreditAmountInput
    ) {
        val creditUrl = "$baseUrl/$accountId"

        val expectedMessage = "Account $accountId credited with amount ${creditAmountInput.amount}"

        mockMvc
            .patch(creditUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(creditAmountInput)
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("true") }
                jsonPath("$.message") { value(expectedMessage) }
            }
    }
}