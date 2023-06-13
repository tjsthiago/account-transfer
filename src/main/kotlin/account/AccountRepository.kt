package account

interface AccountRepository {

    fun get(id: String): Account

    fun save(account: Account): Account

    fun update(account: Account): Account

}