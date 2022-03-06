package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import com.nttdata.accounts.exceptions.customs.CustomNotFoundException;
import com.nttdata.accounts.repository.AccountRepository;
import com.nttdata.accounts.utilities.Validations;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.Utilities;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

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
                .switchIfEmpty(accountRepository.countByClientDocumentNumberAndType(account.getClient().getDocumentNumber(), account.getTypeAccount().getOption())
                        .map(a -> Validations.validateCreateAccount(a, account))
                        .then(Mono.just(account))
                        .flatMap(a -> accountRepository.save(a)
                                .map(b -> {
                                    logger.info("Created a new id = {} for the account with number= {}", account.getId(), account.getNumber());
                                    return b;
                                })));
    }

    @Override
    public Mono<Account> updateBalance(String id,
                                       BigDecimal amount) {
        return accountRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Not found account.")))
                .flatMap(account -> {
                    account.setBalance(account.getBalance().add(amount));
                    logger.info("Update balance for the account with id = {}", account.getId());
                    return accountRepository.save(account);
                });
    }

    @Override
    public Mono<Account> delete(String id) {
        return accountRepository.findById(new ObjectId(id))
                .flatMap(account -> {
                    account.setStatus(false);
                    logger.info("Delete the account with id = {}", account.getId());
                    return accountRepository.save(account);
                });
    }
}
