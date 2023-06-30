package com

import com.infra.driven.repository.AccountInMemoryPersistenceAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Bean
    fun getAccountPersistencePort(): AccountInMemoryPersistenceAdapter {
        return AccountInMemoryPersistenceAdapter()
    }
}