package com.nttdata.accounts.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.nttdata.accounts.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Custom account repository implementation.
 */
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {
  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<Account> findByClientFirstName(String firstName) {
    Query query = new Query(where("client.firstName").is(firstName));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Flux<Account> findByClientFirstNameAndLastName(String firstName,
                                                        String lastName) {
    Query query = new Query(where("client.firstName").is(firstName)
        .and("client.lastName").is(lastName));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Flux<Account> findByClientDocumentNumber(String documentNumber) {
    Query query = new Query(where("client.documentNumber").is(documentNumber));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Mono<Long> countByClientDocumentNumberAndType(String documentNumber, Integer option) {
    Query query = new Query(where("client.documentNumber").is(documentNumber)
        .and("typeAccount.option").is(option));
    return mongoTemplate.count(query, Account.class);
  }
}
