package usecase.account.transfer

data class AccountTransferUseCaseInput (
    val from: String,
    val to: String,
    val amount: Double
)