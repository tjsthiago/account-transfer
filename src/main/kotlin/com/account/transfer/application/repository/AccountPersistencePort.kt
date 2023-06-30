package com.account.transfer.application.repository

import com.account.transfer.domain.entities.Account

interface AccountPersistencePort {

    fun findByAccountId(id: Long): Account

    fun save(account: Account)

    fun update(account: Account)

    fun findAll(): List<Account>

    fun deleteAll()

}