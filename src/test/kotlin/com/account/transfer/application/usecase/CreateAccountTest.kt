package com.account.transfer.application.usecase

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.domain.exceptions.DuplicateAccountException
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreateAccountTest {

    @Autowired
    //@Qualifier("AccountInMemoryPersistenceAdapter")
    @Qualifier("AccountPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Autowired
    @Qualifier("AccountCreatedMessageMockAdapter")
    private lateinit var accountCreatedMessageMockAdapter: AccountCreatedMessagePort

    @BeforeEach
    fun before() {
        accountPersistencePort.deleteAll()
    }

    @Test
    fun `should create a new account`() {
        val createAccount = CreateAccount(
            accountPersistencePort,
            accountCreatedMessageMockAdapter
        )

        val initialAccountBalance = 00.0
        val accountId = 987654L
        val input = CreateAccountInput(accountId)

        val output = createAccount.execute(input)

        val createdAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(output.success)
        assertEquals(initialAccountBalance, createdAccount.balance)
    }

    @Test
    fun `should throw DuplicateAccountException when try to create an account with duplicated accountId`() {
        val createAccount = CreateAccount(
            accountPersistencePort,
            accountCreatedMessageMockAdapter
        )

        val accountId = 123456L
        val input = CreateAccountInput(accountId)

        createAccount.execute(input)

        val exception = assertThrows<DuplicateAccountException> {
            createAccount.execute(input)
        }

        assertEquals("Account with ID $accountId already exists", exception.message)

    }

}