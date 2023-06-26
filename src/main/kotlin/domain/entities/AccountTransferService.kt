package domain.entities

class AccountTransferService {

    fun transfer(from: Account, to: Account, amount: Double) {
        from.debit(amount)
        to.credit(amount)
    }

}