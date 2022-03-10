package com.nttdata.accounts.service;

import com.nttdata.accounts.dto.response.CreditResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Credit service implementation.
 */
@Service
public class CreditServiceImpl implements CreditService {
  @Value("${backend.service.credit}")
  private String urlCredit;

  @Autowired
  @Qualifier("wcLoadBalanced")
  private WebClient.Builder webClient;

  @Override
  public Mono<CreditResponse> consumeClientOwnsCreditCard(String documentNumber) {
    return webClient
        .build()
        .get()
        .uri(urlCredit + "/clientOwnsCard/{documentNumber}", documentNumber)
        .retrieve()
        .bodyToMono(CreditResponse.class)
        .map(credit -> credit)
        .switchIfEmpty(Mono.empty());
  }
}
