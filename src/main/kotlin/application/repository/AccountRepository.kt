package application.repository

import domain.entities.Account

interface AccountRepository {

    fun get(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

}