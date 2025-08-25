package com.account.transfer.application.exceptions

import com.account.transfer.domain.exceptions.InsufficientBalanceException
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler

@Aspect
@Component
class ExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInvalidStatusUpdateException(e: InsufficientBalanceException) {
        println("Exception: ${e.message}")
    }

}
