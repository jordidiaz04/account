package com.nttdata.accounts.service;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import com.nttdata.accounts.exceptions.customs.CustomNotFoundException;
import com.nttdata.accounts.repository.AccountRepository;
import com.nttdata.accounts.utilities.Constants;
import com.nttdata.accounts.utilities.Validations;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account service implementation.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

  private static final String FLUX_NOT_FOUND_MESSAGE = "Data not found";
  private static final String MONO_NOT_FOUND_MESSAGE = "Account not found";

  private final AccountRepository accountRepository;
  private final CreditService creditService;

  @Override
  public Flux<Account> findByClientDocumentNumber(String documentNumber) {
    return accountRepository.findByClientDocumentNumber(documentNumber)
        .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Flux<Account> findByDebitCard(String debitCard) {
    return accountRepository.findByDebitCard(debitCard)
        .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Account> findById(String id) {
    return accountRepository.findById(new ObjectId(id))
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Account> findByNumber(String number) {
    return accountRepository.findByNumber(number)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<BigDecimal> getTotalBalanceByDebitCard(String debitCard) {
    return accountRepository.findByDebitCard(debitCard)
        .map(Account::getBalance)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Account> create(Account account) {
    return accountRepository.findByNumber(account.getNumber())
        .doOnNext(ac -> {
          throw new CustomInformationException("Account number has already been created");
        })
        .switchIfEmpty(Validations.validateFields(account)
            .flatMap(a -> accountRepository
                .countByClientDocumentNumberAndType(account.getClient().getDocumentNumber(),
                    account.getTypeAccount().getOption())
                .flatMap(co -> Validations.validateCreateAccount(co, account))
                .flatMap(this::checkIfRequiresCreditCard)
                .flatMap(this::checkIfHasDebt)
                .flatMap(this::setPosition)
                .flatMap(ac -> accountRepository.save(ac)
                    .map(c -> {
                      logger.info("Created a new id = {} for the account with number= {}",
                          account.getId(), account.getNumber());
                      return c;
                    })))
        );
  }

  @Override
  public Mono<Account> updateBalance(String id,
                                     BigDecimal amount) {
    return accountRepository.findById(new ObjectId(id))
        .flatMap(account -> {
          account.setBalance(account.getBalance().add(amount));
          logger.info("Update balance for the account with id = {}", account.getId());
          return accountRepository.save(account);
        })
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
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

  private Mono<Account> checkIfRequiresCreditCard(Account account) {
    if ((account.getClient().getType() == Constants.ClientType.PERSONAL
        && account.getClient().getProfile() == Constants.ClientProfile.VIP
        && account.getTypeAccount().getOption() == Constants.AccountType.SAVING)
        || (account.getClient().getType() == Constants.ClientType.BUSINESS
        && account.getClient().getProfile() == Constants.ClientProfile.PYME
        && account.getTypeAccount().getOption() == Constants.AccountType.CHECKING)) {
      return creditService.consumeClientOwnsCreditCard(
              account.getClient().getDocumentNumber())
          .switchIfEmpty(
              Mono.error(new CustomInformationException("The account type requires "
                  + "that the client owns a credit card")))
          .flatMap(cr -> Mono.just(account));
    } else {
      return Mono.just(account);
    }
  }

  private Mono<Account> checkIfHasDebt(Account account) {
    return creditService.checkIfClientHasDebts(account.getClient().getDocumentNumber())
        .flatMap(res -> {
          if (Boolean.TRUE.equals(res)) {
            return Mono.error(new CustomInformationException("You cannot create an account "
                + "because you have a credit debt"));
          } else {
            return Mono.just(account);
          }
        });
  }

  private Mono<Account> setPosition(Account account) {
    return accountRepository.getLastByDebitCard(account.getDebitCard())
        .map(ac -> {
          int pos = ac.getPosition() + 1;
          account.setPosition(pos);
          return account;
        })
        .switchIfEmpty(Mono.just(account));
  }


  @Override
  public Flux<Account> findAll() {
    return accountRepository.findAll();
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

}
