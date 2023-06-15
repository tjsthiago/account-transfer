package ports.driven.gateway

import entity.Account

interface AccountRepository {

    fun get(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

}