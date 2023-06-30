package infra.driven.repository

import com.application.repository.AccountPersistencePort
import com.domain.entities.Account
import com.infra.driven.repository.AccountInMemoryPersistenceAdapter
import com.infra.driven.repository.AccountNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryAccountRepositoryTest {

    @Test
    fun `should save an account`() {
        val accountPersistencePort: AccountPersistencePort = AccountInMemoryPersistenceAdapter()

        val initialAccountListSize = accountPersistencePort.findAll().size

        val account = Account(9875654L)
        accountPersistencePort.save(account)

        val accountListSizeAfterSaveNewAccount = accountPersistencePort.findAll().size

        assertEquals(0, initialAccountListSize)
        assertEquals(1, accountListSizeAfterSaveNewAccount)
    }

    @Test
    fun `should get an account`() {
        val accountPersistencePort: AccountPersistencePort = AccountInMemoryPersistenceAdapter()

        val accountId = 9875654L
        val account = Account(accountId)
        accountPersistencePort.save(account)

        val persisted = accountPersistencePort.findById(accountId)

        assertNotNull(persisted)
    }

    @Test
    fun `given one nonexistent account id should throw an AccountNotFoundException`() {
        val accountPersistencePort: AccountPersistencePort = AccountInMemoryPersistenceAdapter()

        val nonexistentAccountId = 0L

        val account = Account(9875654L)
        accountPersistencePort.save(account)

        val thrown: AccountNotFoundException = assertThrows(
            AccountNotFoundException::class.java,
            { accountPersistencePort.findById(nonexistentAccountId) },
            "AccountNotFoundException was expected"
        )

        assertEquals("Account with id[$nonexistentAccountId] not found.", thrown.message)
        assertTrue(accountPersistencePort.findAll().isNotEmpty())
    }

    @Test
    fun `should update an account`() {
        val accountPersistencePort: AccountPersistencePort = AccountInMemoryPersistenceAdapter()

        val accountId = 9875654L
        val account = Account(accountId)
        accountPersistencePort.save(account)

        val persisted = accountPersistencePort.findById(accountId)
        val initialBallance = persisted.balance

        persisted.credit(50.0)
        accountPersistencePort.save(persisted)

        val updated = accountPersistencePort.findById(accountId)

        assertEquals(0.0, initialBallance)
        assertEquals(50.0, updated.balance)
    }
}