package com.account.transfer.infra.driver.messaging

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountInputMessage (
    @JsonProperty("accountId")
    val accountId: Long
)