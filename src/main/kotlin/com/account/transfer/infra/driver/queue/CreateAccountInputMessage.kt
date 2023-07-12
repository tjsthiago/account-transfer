package com.account.transfer.infra.driver.queue

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAccountInputMessage (
    @JsonProperty("accountId")
    val accountId: Long
)