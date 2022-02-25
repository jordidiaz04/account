package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import reactor.core.publisher.Mono;

public interface CustomAccountRepository {
    Mono<Account> findByClientFirstName(String firstName);
    Mono<Account> findByClientDocumentNumber(String documentNumber);
}
