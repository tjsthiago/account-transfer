package com.account.transfer.infra.driver.messaging.account.amount.transfer

import com.fasterxml.jackson.annotation.JsonProperty

data class TransferAmountInputMessage (
    @JsonProperty("from")
    val from: Long,

    @JsonProperty("to")
    val to: Long,

    @JsonProperty("amount")
    val amount: Double
)
