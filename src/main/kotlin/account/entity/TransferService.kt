package account.entity

class TransferService {

    fun transfer(from: Account, to: Account, amount: Double) {
        from.debit(amount)
        to.credit(amount)
    }

}