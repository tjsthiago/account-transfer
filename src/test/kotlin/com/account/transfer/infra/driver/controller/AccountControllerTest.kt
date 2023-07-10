package com.account.transfer.infra.driver.controller

import com.account.transfer.infra.driver.CreateAccountRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
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
}