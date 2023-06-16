package ports.driven

import domain.entity.Account

interface AccountRepository {

    fun get(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

}