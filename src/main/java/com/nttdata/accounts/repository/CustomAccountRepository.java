package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import reactor.core.publisher.Flux;

public interface CustomAccountRepository {
    Flux<Account> findByClientFirstName(String firstName);
    Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName);
    Flux<Account> findByClientDocumentNumber(String documentNumber);
}
