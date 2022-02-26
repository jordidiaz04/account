package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountService {
    Flux<Account> findAll();
    Mono<Account> findById(String id);
    Mono<Account> findByClientFirstName(String firstName);
    Mono<Account> findByClientFirstNameAndLastName(String firstName, String lastName);
    Mono<Account> findByClientDocumentNumber(String documentNumber);
    Mono<Account> findByNumber(String number);
    void create(Account account);
    Mono<Account> update(Account account);
    Mono<Account> updateBalance(String id, BigDecimal amount);
    Mono<Account> delete(String id);
}
