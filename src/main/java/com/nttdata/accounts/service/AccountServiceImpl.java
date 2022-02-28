package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import com.nttdata.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(new ObjectId(id));
    }

    @Override
    public Flux<Account> findByClientFirstName(String firstName) {
        return accountRepository.findByClientFirstName(firstName);
    }

    @Override
    public Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName) {
        return accountRepository.findByClientFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Flux<Account> findByClientDocumentNumber(String documentNumber) {
        return accountRepository.findByClientDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Mono<Account> create(Account account) {
        return accountRepository.findByNumber(account.getNumber())
                .switchIfEmpty(accountRepository.insert(account)
                        .map(x -> x))
                .map(x -> {
                    throw new CustomInformationException("Account number has already been created");
                });
    }

    @Override
    public Mono<Account> update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateBalance(String id,
                                       BigDecimal amount) {
        return accountRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new ClassNotFoundException("Not found account.")))
                .flatMap(account -> {
                    account.setBalance(account.getBalance().add(amount));
                    return update(account);
                });
    }

    @Override
    public Mono<Account> delete(String id) {
        return accountRepository.findById(new ObjectId(id))
                .map(account -> {
                    account.setStatus(false);
                    accountRepository.save(account).subscribe();
                    return account;
                });
    }
}
