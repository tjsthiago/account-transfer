package application.repository

import domain.entities.Account

interface AccountRepository {

    fun findById(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

    fun findAll(): List<Account>

}