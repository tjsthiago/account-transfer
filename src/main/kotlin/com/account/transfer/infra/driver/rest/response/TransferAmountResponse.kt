package com.account.transfer.infra.driver.rest.response

data class TransferAmountResponse (
    val success: Boolean,
    val message: String? = ""
)