package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountService {
    Flux<Account> findAll();
    Mono<Account> findById(String id);
    Flux<Account> findByClientFirstName(String firstName);
    Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName);
    Flux<Account> findByClientDocumentNumber(String documentNumber);
    Mono<Account> findByNumber(String number);
    Mono<Account> create(Account account);
    Mono<Account> updateBalance(String id, BigDecimal amount);
    Mono<Account> delete(String id);
}
