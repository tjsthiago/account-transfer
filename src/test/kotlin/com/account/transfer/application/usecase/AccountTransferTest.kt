package com.account.transfer.application.usecase

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import com.account.transfer.domain.entities.AccountTransferService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
class AccountTransferTest {

    @Autowired
    @Qualifier("AccountPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Test
    fun `should transfer amount between two accounts`() {
        val accountTransferService = AccountTransferService()

        val transferUseCase = AccountTransfer(
            accountPersistencePort,
            accountTransferService
        )

        val from = Account(987654L)
        from.credit(100.0)
        accountPersistencePort.save(from)

        val to = Account(456123)
        accountPersistencePort.save(to)

        val amount = 50.0

        val input = AccountTransferInput(
            from.getAccountId(),
            to.getAccountId(),
            amount
        )

        val result = transferUseCase.execute(input)

        assertTrue(result.success)
    }
}