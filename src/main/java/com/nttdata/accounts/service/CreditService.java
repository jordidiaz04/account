package com.nttdata.accounts.service;

import com.nttdata.accounts.dto.response.CreditResponse;
import reactor.core.publisher.Mono;

/**
 * Credit service interface.
 */
public interface CreditService {
  Mono<CreditResponse> consumeClientOwnsCreditCard(String number);
}
