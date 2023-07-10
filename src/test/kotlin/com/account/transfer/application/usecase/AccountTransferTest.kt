package com.account.transfer.application.usecase

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.domain.entities.Account
import com.account.transfer.domain.entities.AccountTransferService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
class AccountTransferTest {

    @Autowired
    @Qualifier("AccountPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @BeforeEach
    fun before() {
        accountPersistencePort.deleteAll()
    }

    @Test
    fun `should transfer amount between two accounts`() {
        val accountTransferService = AccountTransferService()

        val transferUseCase = AccountTransfer(
            accountPersistencePort,
            accountTransferService
        )

        val accountIdFrom = 987654L
        val from = Account(accountIdFrom)
        from.credit(100.0)
        accountPersistencePort.save(from)

        val accountIdTo = 456123L
        val to = Account(accountIdTo)
        accountPersistencePort.save(to)

        val amount = 50.0

        val input = AccountTransferInput(
            from.getAccountId(),
            to.getAccountId(),
            amount
        )

        val result = transferUseCase.execute(input)

        val fromAccontAfterTransfer = accountPersistencePort.findByAccountId(accountIdFrom)
        val toAccontAfterTransfer = accountPersistencePort.findByAccountId(accountIdTo)

        assertTrue(result.success)
        assertEquals(50.0, fromAccontAfterTransfer.balance)
        assertEquals(50.0, toAccontAfterTransfer.balance)
    }
}