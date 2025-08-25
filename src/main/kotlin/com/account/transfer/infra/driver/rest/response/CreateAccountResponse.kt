package com.account.transfer.infra.driver.rest.response

data class CreateAccountResponse (
    val success: Boolean,
    val message: String? = ""
)