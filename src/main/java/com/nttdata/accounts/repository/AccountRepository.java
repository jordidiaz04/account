package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, ObjectId>, CustomAccountRepository {
    Mono<Account> findByNumber(String number);
}
