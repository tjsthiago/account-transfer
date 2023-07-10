package com.account.transfer.application.usecase

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.create.CreateAccount
import com.account.transfer.application.usecase.create.CreateAccountInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreateAccountTest {

    @Autowired
    @Qualifier("AccountInMemoryPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @BeforeEach
    fun before() {
        accountPersistencePort.deleteAll()
    }

    @Test
    fun `should create a new account`() {
        val initialAccountBalance = 00.0
        val accountId = 987654L
        val input = CreateAccountInput(accountId)

        val createAccount = CreateAccount(accountPersistencePort)

        val output = createAccount.execute(input)

        val createdAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(output.success)
        assertEquals(initialAccountBalance, createdAccount.balance)
    }

}