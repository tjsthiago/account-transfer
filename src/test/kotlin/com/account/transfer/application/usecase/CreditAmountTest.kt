package com.account.transfer.application.usecase

import com.account.transfer.application.messaging.AccountCreatedMessagePort
import com.account.transfer.application.messaging.AmountCreditedMessagePort
import com.account.transfer.application.repository.AccountPersistencePort
import com.account.transfer.application.usecase.ammount.credit.CreditAmount
import com.account.transfer.application.usecase.account.create.CreateAccount
import com.account.transfer.application.usecase.account.create.Input as CreateAccountInput
import com.account.transfer.application.usecase.ammount.credit.Input as CreditAmountInput
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

    @Autowired
    @Qualifier("AccountCreatedMessageMockAdapter")
    private lateinit var accountCreatedMessagePort: AccountCreatedMessagePort

    @Autowired
    @Qualifier("AmountCreditedMessageMockAdapter")
    private lateinit var amountCreditedMessagePort: AmountCreditedMessagePort

    @Test
    fun `should credit an account`() {
        val createAccount = CreateAccount(
            accountPersistencePort,
            accountCreatedMessagePort
        )

        val creditAmount = CreditAmount(
            accountPersistencePort,
            amountCreditedMessagePort
        )

        val amountToCredit = 50.0
        val accountId = 987654L

        val createAccountInput = CreateAccountInput(accountId)
        val createAccountOutput = createAccount.execute(createAccountInput)

        val creditAmountInput = CreditAmountInput(accountId, amountToCredit)
        val creditAmountOutput = creditAmount.execute(creditAmountInput)

        val creditedAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(createAccountOutput.success)
        assertTrue(creditAmountOutput.success)
        assertEquals(amountToCredit, creditedAccount.balance)
    }
}