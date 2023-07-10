package com.account.transfer.application.usecase

import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.ammount.credit.CreditAmountInput
import com.account.transfer.application.usecase.create.CreateAccount
import com.account.transfer.application.usecase.create.CreateAccountInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreditAmountTest {

    @Autowired
    @Qualifier("AccountInMemoryPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Test
    fun `should credit an account`() {
        val createAccount = CreateAccount(accountPersistencePort)
        val creditAmount = CreditAmount(accountPersistencePort)

        val amountToCredit = 50.0
        val accountId = 987654L

        val createAccountInput = CreateAccountInput(accountId)
        val createAccountOutput = createAccount.execute(createAccountInput)

        val creditAmountInput = CreditAmountInput(accountId, 50.0)
        val creditAmountOutput = creditAmount.execute(creditAmountInput)

        val creditedAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(createAccountOutput.success)
        assertTrue(creditAmountOutput.success)
        assertEquals(amountToCredit, creditedAccount.balance)
    }
}