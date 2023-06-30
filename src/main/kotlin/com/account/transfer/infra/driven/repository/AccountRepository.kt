package com.account.transfer.infra.driven.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {
    fun findByAccountId(id: Long): Optional<AccountEntity>

}