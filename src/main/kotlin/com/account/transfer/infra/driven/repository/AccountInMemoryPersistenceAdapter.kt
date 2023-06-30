package com.account.transfer.infra.driven.repository

import com.account.transfer.domain.entities.Account
import com.account.transfer.application.repository.AccountPersistencePort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
@Qualifier("AccountInMemoryPersistenceAdapter")
class AccountInMemoryPersistenceAdapter : AccountPersistencePort {
    private var accounts = mutableListOf<Account>()
    override fun findByAccountId(id: Long): Account {
        return accounts.firstOrNull { it.getId() == id }
            ?: throw AccountNotFoundException("Account with id[$id] not found.")
    }

    override fun save(account: Account) {
        val existingAccount = accounts.firstOrNull { it.getId() == account.getId() }

        if (existingAccount == null) {
            accounts.add(account)
        }
    }

    override fun update(account: Account) {
        val existingAccount = accounts.firstOrNull { it.getId() == account.getId() }

        existingAccount?.let {
            accounts.remove(existingAccount)
            accounts.add(account)
        }
    }

    override fun findAll(): List<Account> {
        return accounts
    }

    override fun deleteAll() {
        accounts = mutableListOf()
    }

}