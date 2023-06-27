package application.usecase

data class AccountTransferInput (
    val from: String,
    val to: String,
    val amount: Double
)