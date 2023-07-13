package com.account.transfer.infra.driver.rest.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CreditAmountResponse (
    @JsonProperty("success")
    val success: Boolean
)