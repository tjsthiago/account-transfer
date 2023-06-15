package ports.driven.repository

import entity.Account

interface AccountRepository {

    fun get(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

}