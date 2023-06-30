package com.account.transfer.infra.driven.repository

import jakarta.persistence.*

@Entity(name = "account")
data class AccountEntity (
    @Column(name = "account_id")
    val accountId: Long,

    @Column(name = "balance")
    val balance: Double,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?=null,
)