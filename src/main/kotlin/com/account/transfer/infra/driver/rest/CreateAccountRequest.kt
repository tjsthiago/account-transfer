package com.account.transfer.infra.driver.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountRequest (
    @JsonProperty("accountId")
    val accountId: Long
)