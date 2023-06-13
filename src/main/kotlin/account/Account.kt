package account

class Account(
    private val id: String
) {

    var balance: Int = 0
        private set

    fun credit(amount: Int) {
        this.balance += amount
    }

    fun debit(amount: Int) {
        this.balance -= amount
    }

}
