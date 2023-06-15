package account.gateway

import account.entity.Account

interface AccountGateway {

    fun get(id: String): Account

    fun save(account: Account)

    fun update(account: Account)

}