package com.account.transfer.infra.driver.rest.account.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class CreateAccountRequest (
    @field:JsonProperty("accountId")
    @field:NotNull(message = "O campo accountId é obrigatório.")
    val accountId: Long?,

    @field:JsonProperty("requestedBy")
    val requestedBy: String?
)