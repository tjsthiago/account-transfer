package com.account.transfer.infra.driver.rest.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreditAmountRequest (
    @JsonProperty("amount")
    val amount: Double
)