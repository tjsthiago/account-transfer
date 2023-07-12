package com.account.transfer.infra.driver.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreditAmountRequest (
    @JsonProperty("amount")
    val amount: Double
)