package com.account.transfer.infra.driver.rest.account.response

data class TransferAmountResponse (
    val success: Boolean,
    val message: String? = ""
)