package com.nttdata.accounts.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import com.nttdata.accounts.dto.request.AccountRequest;
import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.service.AccountService;
import java.math.BigDecimal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RestController for account service.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
  private final AccountService accountService;

  @Value("${application.config.description}")
  private String description;

  @GetMapping("/test")
  public Mono<String> find() {
    return Mono.just(description);
  }

  @GetMapping(value = "/get/client/documentNumber/{documentNumber}",
      produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Account> findByClientDocumentNumber(@PathVariable String documentNumber) {
    return accountService.findByClientDocumentNumber(documentNumber);
  }

  @GetMapping("/get/number/{number}")
  public Mono<Account> findByNumber(@PathVariable String number) {
    return accountService.findByNumber(number);
  }

  @PostMapping("/create")
  @ResponseStatus(CREATED)
  public Mono<Account> create(@Valid @RequestBody AccountRequest request) {
    Account account = new Account(request);
    return accountService.create(account);
  }

  @PutMapping("/balance/{id}/amount/{amount}")
  public Mono<Account> updateBalance(@PathVariable String id,
                                     @PathVariable BigDecimal amount) {
    return accountService.updateBalance(id, amount);
  }


  @GetMapping(value = "/get/all", produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Account> findAll() {
    return accountService.findAll();
  }

  @GetMapping("/get/{id}")
  public Mono<Account> findById(@PathVariable String id) {
    return accountService.findById(id);
  }

  @GetMapping(value = "/get/client/firstName/{firstName}", produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Account> findByClientFirstName(@PathVariable String firstName) {
    return accountService.findByClientFirstName(firstName);
  }

  @GetMapping(value = "/get/client/firstName/{firstName}/lastName/{lastName}",
      produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Account> findByClientFirstNameAndLastName(@PathVariable String firstName,
                                                        @PathVariable String lastName) {
    return accountService.findByClientFirstNameAndLastName(firstName, lastName);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<Account> delete(@PathVariable String id) {
    return accountService.delete(id);
  }
}
