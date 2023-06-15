package account.usecase

data class TransferUseCaseInput (
    val from: String,
    val to: String,
    val amount: Double
)