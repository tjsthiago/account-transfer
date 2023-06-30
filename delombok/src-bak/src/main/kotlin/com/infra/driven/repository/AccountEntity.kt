package com.infra.driven.repository

import jakarta.persistence.*

@Entity(name = "account")
data class AccountEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "balance")
    val balance: Double,
)