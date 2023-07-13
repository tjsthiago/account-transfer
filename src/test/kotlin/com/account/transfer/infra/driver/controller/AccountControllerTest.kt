package com.account.transfer.infra.driver.controller

import com.account.transfer.infra.driver.rest.request.CreateAccountRequest
import com.account.transfer.infra.driver.rest.request.TransferAmountRequest
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

        mockMvc
            .post("$baseUrl/transfer") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(transferAmountRequest)
            }
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.success") { value("true") }
            }
    }

    private fun createAccount(request: CreateAccountRequest) {
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
            }
    }

    private fun credit(
        accountId: Long,
        creditAmountInput: CreditAmountInput
    ) {
        val creditUrl = "$baseUrl/$accountId"

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
            }
    }
}