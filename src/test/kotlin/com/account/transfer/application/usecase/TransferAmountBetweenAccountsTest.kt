package com.account.transfer.application.usecase

import com.account.transfer.application.messaging.AmountBetweenAccountsTransferredMessagePort
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.ammount.transfer.TransferAmountBetweenAccounts
import com.account.transfer.application.usecase.ammount.transfer.Input as TransferAmountInput
import com.account.transfer.domain.entities.Account
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransferAmountBetweenAccountsTest {

    @Autowired
    @Qualifier("AccountInMemoryPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Autowired
    @Qualifier("AmountBetweenAccountsTransferredMessageMockAdapter")
    private lateinit var amountBetweenAccountsTransferredMessagePort: AmountBetweenAccountsTransferredMessagePort

    @BeforeEach
    fun before() {
        accountPersistencePort.deleteAll()
    }

    @Test
    fun `should transfer amount between two accounts`() {
        val transferAmountBetweenAccounts = TransferAmountBetweenAccounts(
            accountPersistencePort,
            amountBetweenAccountsTransferredMessagePort
        )

        val accountIdFrom = 987654L
        val from = Account(accountIdFrom)
        from.credit(100.0)

        accountPersistencePort.save(from)

        val accountIdTo = 456123L
        val to = Account(accountIdTo)

        accountPersistencePort.save(to)

        val amount = 50.0

        val input = TransferAmountInput(
            from.getAccountId(),
            to.getAccountId(),
            amount
        )

        val output = transferAmountBetweenAccounts.execute(input)

        val fromAccountAfterTransfer = accountPersistencePort.findByAccountId(accountIdFrom)
        val toAccountAfterTransfer = accountPersistencePort.findByAccountId(accountIdTo)

        assertTrue(output.success)
        assertEquals(50.0, fromAccountAfterTransfer.balance)
        assertEquals(50.0, toAccountAfterTransfer.balance)
    }
}