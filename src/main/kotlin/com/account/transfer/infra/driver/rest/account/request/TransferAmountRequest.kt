package com.account.transfer.infra.driver.rest.account.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TransferAmountRequest (
    @JsonProperty("from")
    val from: Long,

    @JsonProperty("to")
    val to: Long,

    @JsonProperty("amount")
    val amount: Double
)