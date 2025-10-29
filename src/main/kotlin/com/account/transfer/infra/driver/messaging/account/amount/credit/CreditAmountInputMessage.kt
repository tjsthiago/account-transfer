package com.account.transfer.infra.driver.messaging.account.amount.credit

import com.fasterxml.jackson.annotation.JsonProperty

data class CreditAmountInputMessage(
    @JsonProperty("accountId")
    val accountId: Long,

    @JsonProperty("amount")
    val amount: Double
)
