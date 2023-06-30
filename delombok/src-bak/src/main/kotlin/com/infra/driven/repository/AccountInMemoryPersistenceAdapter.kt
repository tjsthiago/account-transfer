package com.infra.driven.repository

import com.domain.entities.Account
import com.application.repository.AccountPersistencePort
import org.springframework.stereotype.Repository

@Repository
class AccountInMemoryPersistenceAdapter : AccountPersistencePort {
    private var accounts = mutableListOf<Account>()
    override fun findById(id: Long): Account {
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

}