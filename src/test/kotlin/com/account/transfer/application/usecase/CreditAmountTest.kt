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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreditAmountTest {

    @Autowired
    //@Qualifier("AccountInMemoryPersistenceAdapter")
    @Qualifier("AccountPersistenceAdapter")
    private lateinit var accountPersistencePort: AccountPersistencePort

    @Autowired
    @Qualifier("AccountCreatedMessageMockAdapter")
    private lateinit var accountCreatedMessagePort: AccountCreatedMessagePort

    @Autowired
    @Qualifier("AmountCreditedMessageMockAdapter")
    private lateinit var amountCreditedMessagePort: AmountCreditedMessagePort

    @BeforeEach
    fun before() {
        accountPersistencePort.deleteAll()
    }

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

        val initialAmountToCredit = 50.0
        val accountId = 987654L

        val createAccountInput = CreateAccountInput(accountId)
        val createAccountOutput = createAccount.execute(createAccountInput)

        val creditAmountInput = CreditAmountInput(accountId, initialAmountToCredit)
        val creditAmountOutput = creditAmount.execute(creditAmountInput)

        val creditedAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(createAccountOutput.success)
        assertTrue(creditAmountOutput.success)
        assertEquals(initialAmountToCredit, creditedAccount.balance)

        val secondAmountToCredit = 50.0
        val secondCreditAmountInput = CreditAmountInput(accountId, initialAmountToCredit)
        val secondCreditAmountOutput = creditAmount.execute(secondCreditAmountInput)

        val updatedCreditedAccount = accountPersistencePort.findByAccountId(accountId)

        assertTrue(secondCreditAmountOutput.success)
        assertEquals(initialAmountToCredit + secondAmountToCredit, updatedCreditedAccount.balance)
    }
}