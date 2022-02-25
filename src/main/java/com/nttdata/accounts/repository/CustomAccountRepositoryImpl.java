package com.nttdata.accounts.repository;

import com.nttdata.accounts.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.*;

@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Account> findByClientFirstName(String firstName) {
        Query query = new Query(where("client.firstName").is(firstName));
        return mongoTemplate.findOne(query, Account.class);
    }

    @Override
    public Mono<Account> findByClientDocumentNumber(String documentNumber) {
        Query query = new Query(where("client.documentNumber").is(documentNumber));
        return mongoTemplate.findOne(query, Account.class);
    }
}
