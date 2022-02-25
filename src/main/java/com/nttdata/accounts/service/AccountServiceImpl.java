package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> findByClientFirstName(String firstName) {
        return accountRepository.findByClientFirstName(firstName);
    }

    @Override
    public Mono<Account> findByClientDocumentNumber(String documentNumber) {
        return accountRepository.findByClientDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Account> findByNumber(String number) {
        return findByNumber(number);
    }

    @Override
    public void create(Account account) {
        accountRepository.save(account).subscribe();
    }

    @Override
    public Mono<Account> update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> delete(Integer id) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setStatus(false);
                    accountRepository.save(account).subscribe();
                    return account;
                });
    }
}
