package com.nttdata.accounts;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nttdata.accounts.controller.AccountController;
import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.entity.Client;
import com.nttdata.accounts.entity.TypeAccount;
import com.nttdata.accounts.service.AccountService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AccountController.class)
class AccountsApplicationTests {
  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private AccountService accountService;

  @Test
  void testListByClientDocumentNumber() {
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

    Flux<Account> fluxAccount = Flux.just(account, account1);
    when(accountService.findByClientDocumentNumber("00000001")).thenReturn(fluxAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/client/documentNumber/00000001")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNext(account)
        .expectNext(account1)
        .verifyComplete();
  }

  @Test
  void testListByDebitCard() {
    Account account = new Account();
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Account account1 = new Account();
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    Flux<Account> fluxAccount = Flux.just(account, account1);
    when(accountService.findByDebitCard("4420652012504888")).thenReturn(fluxAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/debitCard/4420652012504888")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNext(account)
        .expectNext(account1)
        .verifyComplete();
  }

  @Test
  void testFindById() {
    ObjectId id = new ObjectId();

    Account account = new Account();
    account.setId(id);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Mono<Account> monoAccount = Mono.just(account);
    when(accountService.findById(id.toString())).thenReturn(monoAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/" + id)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNextMatches(x -> x.getNumber().equals("1234567890"))
        .verifyComplete();
  }

  @Test
  void testFindByNumber() {
    Account account = new Account();
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Mono<Account> monoAccount = Mono.just(account);
    when(accountService.findByNumber("1234567890")).thenReturn(monoAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/number/1234567890")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNextMatches(x -> x.getDebitCard().equals("4420652012504888"))
        .verifyComplete();
  }

  @Test
  void testGetTotalBalanceByDebitCard() {
    Mono<BigDecimal> monoTotal = Mono.just(BigDecimal.valueOf(200));
    when(accountService.getTotalBalanceByDebitCard("4420652012504888")).thenReturn(monoTotal);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/totalBalance/4420652012504888")
        .exchange()
        .expectStatus().isOk()
        .returnResult(BigDecimal.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNextMatches(x -> x.compareTo(BigDecimal.valueOf(200)) == 0)
        .verifyComplete();
  }

  @Test
  void testCreate() {
    Client client = new Client();
    client.setId(new ObjectId());
    client.setDocumentNumber("71489282");
    client.setFirstName("Juan");
    client.setLastName("Perez");
    client.setType(1);
    client.setProfile(1);

    TypeAccount typeAccount = new TypeAccount();
    typeAccount.setOption(1);
    typeAccount.setMaxTransactions(5);
    typeAccount.setCommission(BigDecimal.valueOf(3));

    Account account = new Account();
    account.setPosition(1);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");
    account.setClient(client);
    account.setTypeAccount(typeAccount);
    account.setBalance(BigDecimal.ZERO);

    Mono<Account> monoAccount = Mono.just(account);
    when(accountService.create(account)).thenReturn(monoAccount);

    var responseBody = webTestClient
        .post()
        .uri("/accounts/create")
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .body(Mono.just(account), Account.class)
        .exchange()
        .expectStatus().isCreated();
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
    when(accountService.updateBalance(id.toString(), BigDecimal.valueOf(200))).thenReturn(monoAccount);

    var responseBody = webTestClient
        .put()
        .uri("/accounts/balance/" + id + "/amount/200")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNextMatches(x -> x.getBalance().compareTo(BigDecimal.valueOf(400)) == 0)
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
    when(accountService.delete(id.toString())).thenReturn(monoAccount);

    var responseBody = webTestClient
        .delete()
        .uri("/accounts/delete/" + id)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNextMatches(x -> !x.isStatus())
        .verifyComplete();
  }

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

    Flux<Account> fluxAccount = Flux.just(account, account1);
    when(accountService.findAll()).thenReturn(fluxAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/all")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNext(account)
        .expectNext(account1)
        .verifyComplete();
  }

  @Test
  void testListByClientFirstName() {
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");

    Account account = new Account();
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client1.setFirstName("Juan");

    Account account1 = new Account();
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    Flux<Account> fluxAccount = Flux.just(account, account1);
    when(accountService.findByClientFirstName("Juan")).thenReturn(fluxAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/client/firstName/Juan")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNext(account)
        .expectNext(account1)
        .verifyComplete();
  }

  @Test
  void testListByClientFirstNameAndLastName() {
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setFirstName("Lopez");

    Account account = new Account();
    account.setClient(client);
    account.setNumber("1234567890");
    account.setDebitCard("4420652012504888");

    Client client1 = new Client();
    client1.setDocumentNumber("00000001");
    client1.setFirstName("Juan");
    client.setFirstName("Lopez");

    Account account1 = new Account();
    account1.setClient(client1);
    account1.setNumber("1234567891");
    account1.setDebitCard("4420652012504888");

    Flux<Account> fluxAccount = Flux.just(account, account1);
    when(accountService.findByClientFirstNameAndLastName("Juan", "Lopez")).thenReturn(fluxAccount);

    var responseBody = webTestClient
        .get()
        .uri("/accounts/get/client/firstName/Juan/lastName/Lopez")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Account.class)
        .getResponseBody();

    StepVerifier
        .create(responseBody)
        .expectSubscription()
        .expectNext(account)
        .expectNext(account1)
        .verifyComplete();
  }
}
