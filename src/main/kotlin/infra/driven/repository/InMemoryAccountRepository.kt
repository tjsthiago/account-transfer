package infra.driven.repository

import domain.entities.Account
import application.repository.AccountRepository

class InMemoryAccountRepository : AccountRepository {
    private var accounts = mutableListOf<Account>()
    override fun findById(id: String): Account {
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