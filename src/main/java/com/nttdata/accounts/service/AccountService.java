package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account service interface.
 */
public interface AccountService {
  Flux<Account> findAll();

  Flux<Account> findByClientFirstName(String firstName);

  Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName);

  Flux<Account> findByClientDocumentNumber(String documentNumber);

  Flux<Account> findByDebitCard(String documentNumber);

  Mono<Account> findById(String id);

  Mono<Account> findByNumber(String number);

  Mono<BigDecimal> getTotalBalanceByDebitCard(String debitCard);

  Mono<Account> create(Account account);

  Mono<Account> updateBalance(String id, BigDecimal amount);

  Mono<Account> delete(String id);
}
