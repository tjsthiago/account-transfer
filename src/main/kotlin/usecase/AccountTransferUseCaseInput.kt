package usecase

data class AccountTransferUseCaseInput (
    val from: String,
    val to: String,
    val amount: Double
)