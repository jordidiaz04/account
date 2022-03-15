package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account repository.
 */
@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, ObjectId>,
    CustomAccountRepository {
  Flux<Account> findByDebitCard(String debitCard);

  Mono<Account> findByNumber(String number);
}
