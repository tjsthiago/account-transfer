package com.infra.driven.repository

import com.application.repository.AccountPersistencePort
import com.domain.entities.Account
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapter (
    private val accountRepository: AccountRepository
) : AccountPersistencePort {

    override fun findById(id: Long): Account {
        val persisted = accountRepository.findById(id)

        if(persisted.isPresent)
            return convertPersistenceEntityToDomainEntity(persisted.get())
            throw AccountNotFoundException("Account with id [$id] not found")
    }

    override fun save(account: Account) {
        accountRepository.saveAndFlush(
            convertDomainEntityToPersistenceEntity(account)
        )
    }

    override fun update(account: Account) {
        accountRepository.saveAndFlush(
            convertDomainEntityToPersistenceEntity(account)
        )
    }

    override fun findAll(): List<Account>  {
        val listAsEntity = accountRepository.findAll()

        val listAsModel = listAsEntity.map {
            convertPersistenceEntityToDomainEntity(it)
        }

        return listAsModel
    }

    private fun convertPersistenceEntityToDomainEntity(accountEntity: AccountEntity): Account {
        val account = Account(accountEntity.id)
        account.credit(accountEntity.balance)

        return account
    }

    private fun convertDomainEntityToPersistenceEntity(account: Account) : AccountEntity {
        return AccountEntity(account.getId(), account.balance)
    }

}