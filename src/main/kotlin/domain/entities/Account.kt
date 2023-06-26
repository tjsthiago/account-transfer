package domain.entities

class Account(
    private val id: String
) {
    fun getId() = id

    var balance: Double = 0.0
        private set

    fun credit(amount: Double) {
        this.balance += amount
    }

    fun debit(amount: Double) {
        this.balance -= amount
    }

}
