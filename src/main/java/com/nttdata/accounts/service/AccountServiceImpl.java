package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import com.nttdata.accounts.exceptions.customs.CustomNotFoundException;
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
    private static final String FLUX_NOT_FOUND_MESSAGE = "Data not found";
    private static final String MONO_NOT_FOUND_MESSAGE = "Account not found";
    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Flux<Account> findByClientFirstName(String firstName) {
        return accountRepository.findByClientFirstName(firstName)
                .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName) {
        return accountRepository.findByClientFirstNameAndLastName(firstName, lastName)
                .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Flux<Account> findByClientDocumentNumber(String documentNumber) {
        return accountRepository.findByClientDocumentNumber(documentNumber)
                .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Mono<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number)
                .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
    }

    @Override
    public Mono<Account> create(Account account) {
        return accountRepository.findByNumber(account.getNumber())
                .doOnNext(a -> {
                    throw new CustomInformationException("Account number has already been created");
                })
                .switchIfEmpty(accountRepository.countByClientDocumentNumber(account.getClient().getDocumentNumber())
                        .map(a -> {
                            if (account.getClient().getType() == 1 && a > 0) {
                                throw new CustomInformationException("The customer type can only have 1 account");
                            } else if (account.getClient().getType() == 2 && account.getTypeAccount().getOption() != 2) {
                                throw new CustomInformationException("The type of client can only have current accounts");
                            } else {
                                return a;
                            }
                        })
                        .then(Mono.just(account))
                        .flatMap(accountRepository::save));
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
                .flatMap(account -> {
                    account.setStatus(false);
                    return update(account);
                });
    }
}
