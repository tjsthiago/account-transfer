package com.account.transfer.infra.driver.rest.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TransferAmountResponse (
    @JsonProperty("success")
    val success: Boolean
)