package com.nttdata.accounts;

import static org.mockito.Mockito.when;

import com.nttdata.accounts.dto.response.CreditResponse;
import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.entity.Client;
import com.nttdata.accounts.entity.TypeAccount;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import com.nttdata.accounts.repository.AccountRepository;
import com.nttdata.accounts.service.AccountServiceImpl;
import com.nttdata.accounts.service.CreditServiceImpl;
import com.nttdata.accounts.utilities.Constants;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {
  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Mock
  private CreditServiceImpl creditService;

  @Test
  void testListAll() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findAll()).thenReturn(fluxAccount);

    var accounts = accountService.findAll();
    StepVerifier
        .create(accounts)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567891", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testListByClientFirstName() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findByClientFirstName("Juan")).thenReturn(fluxAccount);

    var accounts = accountService.findByClientFirstName("Juan");
    StepVerifier
        .create(accounts)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567891", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testListByClientFirstNameAndLastName() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setLastName("Lopez");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setLastName("Lopez");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findByClientFirstNameAndLastName("Juan", "Lopez")).thenReturn(fluxAccount);

    var accounts = accountService.findByClientFirstNameAndLastName("Juan", "Lopez");
    StepVerifier
        .create(accounts)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567891", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testListByClientDocumentNumber() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findByClientDocumentNumber("00000001")).thenReturn(fluxAccount);

    var accounts = accountService.findByClientDocumentNumber("00000001");
    StepVerifier
        .create(accounts)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567891", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testListByDebitCard() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findByDebitCard("4420652012504888")).thenReturn(fluxAccount);

    var accounts = accountService.findByDebitCard("4420652012504888");
    StepVerifier
        .create(accounts)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567891", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testFindById() {
    ObjectId id = new ObjectId();

    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");

    Account account = new Account();
    account.setId(id);
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    var monoAccount = Mono.just(account);
    when(accountRepository.findById(id)).thenReturn(monoAccount);

    var resAccount = accountService.findById(id.toString());
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testFindByNumber() {
    ObjectId id = new ObjectId();

    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");

    Account account = new Account();
    account.setId(id);
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(monoAccount);

    var resAccount = accountService.findByNumber("1234567890");
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("4420652012504888", ac.getDebitCard());
        })
        .verifyComplete();
  }

  @Test
  void testGetTotalBalanceByDebitCard() {
    Account account = new Account();
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setBalance(BigDecimal.valueOf(1500));

    Account account1 = new Account();
    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client1.setFirstName("Juan");
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");
    account1.setBalance(BigDecimal.valueOf(500));

    var fluxAccount = Flux.just(account, account1);
    when(accountRepository.findByDebitCard("4420652012504888")).thenReturn(fluxAccount);

    var resTotal = accountService.getTotalBalanceByDebitCard("4420652012504888");
    StepVerifier
        .create(resTotal)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals(BigDecimal.valueOf(2000), ac);
        })
        .verifyComplete();
  }

  @Test
  void testCreatePersonalRegularSaving() {
    Client client = new Client(new ObjectId(), "71489282", "Juan", "Perez", Constants.ClientType.PERSONAL, Constants.ClientProfile.REGULAR);
    TypeAccount typeAccount = new TypeAccount(Constants.AccountType.SAVING, null, 5, BigDecimal.valueOf(3), null);

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.SAVING)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.empty());
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreatePersonalRegularChecking() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(2));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.empty());
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreatePersonalRegularFixedTerm() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.FIXED_TERM);
    typeAccount.setMaxTransactions(5);
    typeAccount.setDay(24);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.FIXED_TERM)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.empty());
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateAccountTypeSavingMaxTransactionNull() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field maxTransactions for typeAccount must be required"))
        .verify();
  }

  @Test
  void testCreateAccountTypeSavingMaxTransactionLessThan1() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaxTransactions(0);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field maxTransactions for typeAccount must be at least 1"))
        .verify();
  }

  @Test
  void testCreateAccountTypeCheckingMaintenanceNull() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field maintenance for typeAccount must be required"))
        .verify();
  }

  @Test
  void testCreateAccountTypeCheckingMaintenanceLessThan0() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(-3));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field maintenance for typeAccount must be greater than 0"))
        .verify();
  }

  @Test
  void testCreateAccountTypeFixedTermDayNull() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.FIXED_TERM);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field day for typeAccount must be required"))
        .verify();
  }

  @Test
  void testCreateAccountTypeFixedTermDayLessThan1() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.FIXED_TERM);
    typeAccount.setMaxTransactions(0);
    typeAccount.setDay(0);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    int lastDayOfMonth = LocalDate
        .now()
        .withDayOfMonth(LocalDate.now().lengthOfMonth()).getDayOfMonth();

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field day for typeAccount must be between 1 and " + lastDayOfMonth))
        .verify();
  }

  @Test
  void testCreateAccountTypeFixedTermDayGreaterThanLastDay() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.FIXED_TERM);
    typeAccount.setMaxTransactions(0);
    typeAccount.setDay(32);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    int lastDayOfMonth = LocalDate
        .now()
        .withDayOfMonth(LocalDate.now().lengthOfMonth()).getDayOfMonth();

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("Field day for typeAccount must be between 1 and " + lastDayOfMonth))
        .verify();
  }

  @Test
  void testCreatePersonalRegularSavingMoreThan1() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.SAVING)).thenReturn(Mono.just(1L));

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("The type of client can only have 1 account of this type"))
        .verify();
  }

  @Test
  void testCreateBusinessRegularNotChecking() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.BUSINESS);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.SAVING)).thenReturn(Mono.just(1L));

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("The type of client can only have multiple current accounts"))
        .verify();
  }

  @Test
  void testCreateBusinessHoldersNull() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.BUSINESS);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(2));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(1L));

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("The account type requires at least one holder"))
        .verify();
  }

  @Test
  void testCreateBusinessHoldersEmpty() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.BUSINESS);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(2));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    List<String> holders = new ArrayList<>();

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setHolders(holders);
    account.setBalance(BigDecimal.ZERO);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(1L));

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("The account type requires at least one holder"))
        .verify();
  }

  @Test
  void testCreateClientPersonalVip() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.VIP);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(3));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.just(new CreditResponse()));
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateClientBusinessRegular() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.BUSINESS);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(3));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    List<String> holders = new ArrayList<>();
    holders.add("Juan Perez");

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setHolders(holders);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.just(new CreditResponse()));
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateClientBusinessPyme() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.BUSINESS);
    client.setProfile(Constants.ClientProfile.PYME);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.CHECKING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setMaintenance(BigDecimal.valueOf(3));
    typeAccount.setCommission(BigDecimal.valueOf(3));

    List<String> holders = new ArrayList<>();
    holders.add("Juan Perez");

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setHolders(holders);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", Constants.AccountType.CHECKING)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.just(new CreditResponse()));
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(false));
    when(accountRepository.getLastByDebitCard("4420652012504888")).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateAccountExist() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaxTransactions(5);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    var monoAccount = Mono.just(account);
    when(accountRepository.findByNumber("1234567890")).thenReturn(monoAccount);

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectError(CustomInformationException.class)
        .verify();
  }

  @Test
  void testCreateCheckRequiresCreditCard() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.VIP);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaintenance(BigDecimal.valueOf(4));
    typeAccount.setMaxTransactions(3);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", 1)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.empty());

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("The account type requires that the client owns a credit card"))
        .verify();
  }

  @Test
  void testCreateHasDebt() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setType(Constants.ClientType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(Constants.AccountType.SAVING);
    typeAccount.setMaintenance(BigDecimal.valueOf(4));
    typeAccount.setMaxTransactions(3);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);

    when(accountRepository.findByNumber("1234567890")).thenReturn(Mono.empty());
    when(accountRepository.countByClientDocumentNumberAndType("71489282", 1)).thenReturn(Mono.just(0L));
    when(creditService.consumeClientOwnsCreditCard("71489282")).thenReturn(Mono.empty());
    when(creditService.checkIfClientHasDebts("71489282")).thenReturn(Mono.just(true));

    var resAccount = accountService.create(account);
    StepVerifier
        .create(resAccount)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("You cannot create an account because you have a credit debt"))
        .verify();
  }

  @Test
  void testUpdateBalance() {
    ObjectId id = new ObjectId();

    Account account = new Account();
    account.setId(id);
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setBalance(BigDecimal.valueOf(400));

    Mono<Account> monoAccount = Mono.just(account);
    when(accountRepository.findById(id)).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.updateBalance(id.toString(), BigDecimal.valueOf(400));
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals(BigDecimal.valueOf(800), ac.getBalance());
        })
        .verifyComplete();
  }

  @Test
  void testDelete() {
    ObjectId id = new ObjectId();

    Account account = new Account();
    account.setId(id);
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setBalance(BigDecimal.valueOf(400));
    account.setStatus(false);

    Mono<Account> monoAccount = Mono.just(account);
    when(accountRepository.findById(id)).thenReturn(monoAccount);
    when(accountRepository.save(account)).thenReturn(monoAccount);

    var resAccount = accountService.delete(id.toString());
    StepVerifier
        .create(resAccount)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("1234567890", ac.getNumber());
        })
        .verifyComplete();
  }
}
