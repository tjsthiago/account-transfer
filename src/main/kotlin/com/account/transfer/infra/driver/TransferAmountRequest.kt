package com.account.transfer.infra.driver

import com.fasterxml.jackson.annotation.JsonProperty

data class TransferAmountRequest (
    @JsonProperty("from")
    val from: Long,

    @JsonProperty("to")
    val to: Long,

    @JsonProperty("amount")
    val amount: Double
)