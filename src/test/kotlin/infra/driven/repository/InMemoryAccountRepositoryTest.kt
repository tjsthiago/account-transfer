package infra.driven.repository

import application.repository.AccountRepository
import domain.entities.Account
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryAccountRepositoryTest {

    @Test
    fun `should save an account`() {
        val accountRepository: AccountRepository = InMemoryAccountRepository()

        val initialAccountListSize = accountRepository.findAll().size

        val account = Account("9875654")
        accountRepository.save(account)

        val accountListSizeAfterSaveNewAccount = accountRepository.findAll().size

        assertEquals(0, initialAccountListSize)
        assertEquals(1, accountListSizeAfterSaveNewAccount)
    }

    @Test
    fun `should get an account`() {
        val accountRepository: AccountRepository = InMemoryAccountRepository()

        val accountId = "9875654"
        val account = Account(accountId)
        accountRepository.save(account)

        val persisted = accountRepository.findById(accountId)

        assertNotNull(persisted)
    }

    @Test
    fun `given one nonexistent account id should throw an AccountNotFoundException`() {
        val accountRepository: AccountRepository = InMemoryAccountRepository()

        val nonexistentAccountId = "00000"

        val account = Account("9875654")
        accountRepository.save(account)

        val thrown: AccountNotFoundException = assertThrows(
            AccountNotFoundException::class.java,
            { accountRepository.findById(nonexistentAccountId) },
            "AccountNotFoundException was expected"
        )

        assertEquals("Account with id[$nonexistentAccountId] not found.", thrown.message)
        assertTrue(accountRepository.findAll().isNotEmpty())
    }

    @Test
    fun `should update an account`() {
        val accountRepository: AccountRepository = InMemoryAccountRepository()

        val accountId = "9875654"
        val account = Account(accountId)
        accountRepository.save(account)

        val persisted = accountRepository.findById(accountId)
        val initialBallance = persisted.balance

        persisted.credit(50.0)
        accountRepository.save(persisted)

        val updated = accountRepository.findById(accountId)

        assertEquals(0.0, initialBallance)
        assertEquals(50.0, updated.balance)
    }
}