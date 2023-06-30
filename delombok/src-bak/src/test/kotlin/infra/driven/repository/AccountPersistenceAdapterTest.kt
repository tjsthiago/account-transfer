package infra.driven.repository

import com.application.repository.AccountPersistencePort
import com.domain.entities.Account
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
class AccountPersistenceAdapterTest (
    private val accountPersistencePort: AccountPersistencePort
){

    @Test
    fun `should save an account`() {
        val initialAccountListSize = accountPersistencePort.findAll().size

        val account = Account(9875654L)
        accountPersistencePort.save(account)

        val accountListSizeAfterSaveNewAccount = accountPersistencePort.findAll().size

        Assertions.assertEquals(0, initialAccountListSize)
        Assertions.assertEquals(1, accountListSizeAfterSaveNewAccount)
    }
}