package com.account.transfer.infra.driven.repository

import com.account.transfer.domain.entities.Account
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Optional

class AccountPersistenceAdapterTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var accountPersistenceAdapter: AccountPersistenceAdapter

    @BeforeEach
    fun setUp() {
        accountRepository = mock()
        accountPersistenceAdapter = AccountPersistenceAdapter(accountRepository)
    }

    @Test
    fun `should save an account`() {
        val account = Account(1L)
        val accountEntity = AccountEntity(account.getAccountId(), account.balance, 1L)
        whenever(accountRepository.saveAndFlush(any<AccountEntity>())).thenReturn(accountEntity)
        whenever(accountRepository.findAll()).thenReturn(listOf(accountEntity))

        accountPersistenceAdapter.save(account)
        val accounts = accountPersistenceAdapter.findAll()
        assertEquals(1, accounts.size)
        assertEquals(account.getAccountId(), accounts[0].getAccountId())
    }

    @Test
    fun `should find an account by account-id`() {
        val accountId = 2L
        val accountEntity = AccountEntity(accountId, 0.0, 1L)
        whenever(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(accountEntity))

        val account = accountPersistenceAdapter.findByAccountId(accountId)
        assertNotNull(account)
        assertEquals(accountId, account.getAccountId())
    }

    @Test
    fun `given one nonexistent account id should throw an AccountNotFoundException`() {
        val nonexistentAccountId = 0L
        whenever(accountRepository.findByAccountId(nonexistentAccountId)).thenReturn(Optional.empty<AccountEntity>())

        val thrown = assertThrows(AccountNotFoundException::class.java) {
            accountPersistenceAdapter.findByAccountId(nonexistentAccountId)
        }
        assertEquals("Account with id [$nonexistentAccountId] not found", thrown.message)
    }

    @Test
    fun `should update an account`() {
        val accountId = 3L
        val accountEntity = AccountEntity(accountId, 0.0, 1L)
        val updatedEntity = AccountEntity(accountId, 50.0, 1L)
        whenever(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(accountEntity))
        whenever(accountRepository.saveAndFlush(any<AccountEntity>())).thenReturn(updatedEntity)

        val account = Account(accountId)
        account.credit(50.0)
        accountPersistenceAdapter.update(account)

        whenever(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(updatedEntity))
        val updated = accountPersistenceAdapter.findByAccountId(accountId)
        assertEquals(50.0, updated.balance)
    }

    @Test
    fun `should assert that account does not exist by accountId`() {
        val accountId = 8L
        whenever(accountRepository.findByAccountId(accountId)).thenReturn(Optional.empty<AccountEntity>())
        val exists = accountPersistenceAdapter.existsByAccountId(accountId)
        assertFalse(exists)
    }

}