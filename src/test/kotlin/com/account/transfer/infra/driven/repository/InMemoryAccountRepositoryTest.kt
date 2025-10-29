package com.account.transfer.infra.driven.repository

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random

@SpringBootTest()
class InMemoryAccountRepositoryTest {

    @Autowired
    @Qualifier("AccountInMemoryPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Test
    fun `should save an account`() {
        val initialAccountListSize = accountPersistencePort.findAll().size

        val account = Account(Random.nextLong(0, 1000))
        accountPersistencePort.save(account)

        val accountListSizeAfterSaveNewAccount = accountPersistencePort.findAll().size

        assertEquals(initialAccountListSize + 1, accountListSizeAfterSaveNewAccount)
    }

    @Test
    fun `should get an account`() {
        val accountId = Random.nextLong(0, 1000)
        val account = Account(accountId)
        accountPersistencePort.save(account)

        val persisted = accountPersistencePort.findByAccountId(accountId)

        assertNotNull(persisted)
    }

    @Test
    fun `given one nonexistent account id should throw an AccountNotFoundException`() {
        val accountId = Random.nextLong(0, 1000)
        val nonexistentAccountId = 0L

        val account = Account(accountId)
        accountPersistencePort.save(account)

        val thrown: AccountNotFoundException = assertThrows(
            AccountNotFoundException::class.java,
            { accountPersistencePort.findByAccountId(nonexistentAccountId) },
            "AccountNotFoundException was expected"
        )

        assertEquals("Account with id [$nonexistentAccountId] not found", thrown.message)
        assertTrue(accountPersistencePort.findAll().isNotEmpty())
    }

    @Test
    fun `should update an account`() {
        val accountId = Random.nextLong(0, 1000)
        val account = Account(accountId)
        accountPersistencePort.save(account)

        val persisted = accountPersistencePort.findByAccountId(accountId)
        val initialBallance = persisted.balance

        persisted.credit(50.0)
        accountPersistencePort.update(persisted)

        val updated = accountPersistencePort.findByAccountId(accountId)

        assertEquals(0.0, initialBallance)
        assertEquals(50.0, updated.balance)
    }

    @Test
    fun `should assert that account does not exist by accountId`() {
        assertFalse(accountPersistencePort.existsByAccountId(8L))
    }
}