package com.account.transfer.infra.driver

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountResponse (

    @JsonProperty("success")
    val success: Boolean
)