package com.account.transfer.infra.driver

import com.fasterxml.jackson.annotation.JsonProperty

data class TransferAmountResponse (
    @JsonProperty("success")
    val success: Boolean
)