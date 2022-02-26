package com.nttdata.accounts.controller;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping(value = "/get/all", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Account>> findById(@PathVariable String id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/client/firstName/{firstName}")
    public Mono<ResponseEntity<Account>> findByClientFirstName(@PathVariable String firstName) {
        return accountService.findByClientFirstName(firstName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/client/firstName/{firstName}/lastName/{lastName}")
    public Mono<ResponseEntity<Account>> findByClientFirstNameAndLastName(@PathVariable String firstName,
                                                                          @PathVariable String lastName) {
        return accountService.findByClientFirstNameAndLastName(firstName, lastName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/get/client/documentNumber/{documentNumber}")
    public Mono<ResponseEntity<Account>> findByClientDocumentNumber(@PathVariable String documentNumber) {
        return accountService.findByClientDocumentNumber(documentNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/number/{number}")
    public Mono<ResponseEntity<Account>> findByNumber(@PathVariable String number) {
        return accountService.findByNumber(number)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public void create(@RequestBody Account account) {
        accountService.create(account);
    }

    @PutMapping("/update")
    public Mono<Account> update(@RequestBody Account account) {
        return accountService.update(account);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Account> delete(@PathVariable String id) {
        return accountService.delete(id);
    }
}
