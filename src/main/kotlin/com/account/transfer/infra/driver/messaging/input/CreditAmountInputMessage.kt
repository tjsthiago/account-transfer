package com.account.transfer.infra.driver.messaging.input

import com.fasterxml.jackson.annotation.JsonProperty

data class CreditAmountInputMessage (
    @JsonProperty("accountId")
    val accountId: Long,

    @JsonProperty("amount")
    val amount: Double
)