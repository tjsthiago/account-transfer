package com.account.transfer.infra.driver.rest.account.response

data class CreateAccountResponse (
    val success: Boolean,
    val message: String? = ""
)