package com.account.transfer.application.usecase

import com.account.transfer.domain.entities.Account
import com.account.transfer.domain.entities.AccountTransferService
import com.account.transfer.infra.driven.repository.AccountInMemoryPersistenceAdapter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AccountTransferTest {

    @Test
    fun `should transfer amount between two accounts`() {
        val accountRepository = AccountInMemoryPersistenceAdapter()
        val accountTransferService = AccountTransferService()

        val transferUseCase = AccountTransfer(
            accountRepository,
            accountTransferService
        )

        val from = Account(987654L)
        from.credit(100.0)
        accountRepository.save(from)

        val to = Account(456123)
        accountRepository.save(to)

        val amount = 50.0

        val input = AccountTransferInput(
            from.getId(),
            to.getId(),
            amount
        )

        val result = transferUseCase.execute(input)

        assertTrue(result.success)
    }
}