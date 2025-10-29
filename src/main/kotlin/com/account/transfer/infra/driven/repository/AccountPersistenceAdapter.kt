package com.account.transfer.infra.driven.repository

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Primary
@Qualifier("AccountPersistenceAdapter")
class AccountPersistenceAdapter (
    private val accountRepository: AccountRepository
) : AccountPersistencePort {
    override fun existsByAccountId(id: Long): Boolean {
        return accountRepository.existsByAccountId(id)
    }

    override fun findByAccountId(id: Long): Account {
        return convertPersistenceEntityToDomainEntity(
            findAccountEntityByAccountId(id)
        )
    }

    private fun findAccountEntityByAccountId(accountId: Long): AccountEntity {
        val persisted = accountRepository.findByAccountId(accountId)

        if(persisted.isPresent)
            return persisted.get()

        throw AccountNotFoundException("Account with id[$accountId] not found.")
    }

    @Transactional
    override fun save(account: Account) {
        val accountEntity = convertDomainEntityToPersistenceEntity(account)

        accountRepository.saveAndFlush(
            accountEntity
        )
    }

    @Transactional
    override fun update(account: Account) {
        val persisted = findAccountEntityByAccountId(account.getAccountId())

        val toUpdate = convertDomainEntityToPersistenceEntity(account).copy(
            id = persisted.id
        )

        accountRepository.saveAndFlush(
            toUpdate
        )
    }

    override fun findAll(): List<Account>  {
        val listAsEntity = accountRepository.findAll()

        val listAsModel = listAsEntity.map {
            convertPersistenceEntityToDomainEntity(it)
        }

        return listAsModel
    }

    @Transactional
    override fun deleteAll() {
        accountRepository.deleteAll()
    }

    private fun convertPersistenceEntityToDomainEntity(accountEntity: AccountEntity): Account {
        val account = Account(accountEntity.accountId)
        account.credit(accountEntity.balance)

        return account
    }

    private fun convertDomainEntityToPersistenceEntity(account: Account) : AccountEntity {
        return AccountEntity(
            account.getAccountId(),
            account.balance
        )
    }

}