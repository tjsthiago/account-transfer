package com.account.transfer.infra.driver.exception

import com.account.transfer.domain.exceptions.DuplicateAccountException
import com.account.transfer.domain.exceptions.InsufficientBalanceException
import com.account.transfer.infra.driven.repository.AccountNotFoundException
import com.account.transfer.infra.driver.rest.AccountController
import com.account.transfer.infra.driver.rest.response.CreateAccountResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [AccountController::class])
class AccountControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(AccountControllerExceptionHandler::class.java)

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(ex: AccountNotFoundException): ResponseEntity<CreateAccountResponse> {
        logger.error("Account not found error", ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            CreateAccountResponse(
                success = false,
                message = ex.message
            )
        )
    }

    @ExceptionHandler(DuplicateAccountException::class)
    fun handleDuplicateAccountException(ex: DuplicateAccountException): ResponseEntity<CreateAccountResponse> {
        logger.error("Duplicate account error", ex)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            CreateAccountResponse(
                success = false,
                message = ex.message
            )
        )
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalanceException(ex: InsufficientBalanceException): ResponseEntity<CreateAccountResponse> {
        logger.error("Insufficient balance error", ex)
        return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(
            CreateAccountResponse(
                success = false,
                message = ex.message
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<CreateAccountResponse> {
        logger.error("Generic error in AccountController", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            CreateAccountResponse(
                success = false,
                message = ex.message
            )
        )
    }
}