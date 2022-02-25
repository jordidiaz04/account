package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, Integer>, CustomAccountRepository {
    Mono<Account> findByNumber(String number);
}
