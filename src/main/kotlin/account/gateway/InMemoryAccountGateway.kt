package account.gateway

import account.entity.Account

class InMemoryAccountGateway : AccountGateway {
    private var accounts = mutableListOf<Account>()
    override fun get(id: String): Account {
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
}