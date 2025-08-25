package com.account.transfer.infra.driver.messaging.input

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountInputMessage(
    @JsonProperty("accountId")
    val accountId: Long
)
