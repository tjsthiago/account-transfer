package com.application.repository

import com.domain.entities.Account

interface AccountPersistencePort {

    fun findById(id: Long): Account

    fun save(account: Account)

    fun update(account: Account)

    fun findAll(): List<Account>

}