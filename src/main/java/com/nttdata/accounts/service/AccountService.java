package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();
    Mono<Account> findById(Integer id);
    Mono<Account> findByClientFirstName(String firstName);
    Mono<Account> findByClientDocumentNumber(String documentNumber);
    Mono<Account> findByNumber(String number);
    void create(Account account);
    Mono<Account> update(Account account);
    Mono<Account> delete(Integer id);
}
