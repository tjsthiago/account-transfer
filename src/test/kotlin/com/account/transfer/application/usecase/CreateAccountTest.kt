package com.account.transfer.application.usecase

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
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

}