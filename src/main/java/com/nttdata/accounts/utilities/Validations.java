package com.nttdata.accounts.utilities;

import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.entity.Credit;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;

import reactor.core.publisher.Mono;

public class Validations {
	
	private static WebClient webClient;
	
    private Validations() {
        throw new IllegalArgumentException("Validations class error.");
    }

    public static Long validateCreateAccount(Long count, Account account) {
        if (account.getClient().getType() == 1 && count > 0) {
            throw new CustomInformationException("The type of client can only have 1 account of this type");
        } else if (account.getClient().getType() == 2 && account.getTypeAccount().getOption() != 2) {
            throw new CustomInformationException("The type of client can only have multiple current accounts");
        } else if (account.getClient().getType() == 2 && (account.getHolders() == null || account.getHolders().isEmpty())) {
            throw new CustomInformationException("The account type requires at least one holder");
        } else {
            return count;
        }
    }
    
    /*public static Long validateClientOwnsCreditCard( String documentNumber) {
    	
    	Long result = 0L;
    	
    	return consumeClientOwnsCreditCard(documentNumber)
    	.flatMap(x -> {
    		if(x == 0)
    			throw new CustomInformationException("The account type requires that the client owns a credit card");
    		else
    			return x
    	}); 
    
    	//return result;
    }*/
    
    @SuppressWarnings("unchecked")
	public static Mono<Credit> consumeClientOwnsCreditCard( String documentNumber) { 
    	
    	String path = "http://localhost:8099/credits";
        String url = path + "/clientOwnsCard/{documentNumber}";
        
        webClient = WebClient.create();
        
        Mono<Credit> result = (Mono<Credit>) webClient.get()
                .uri(url, documentNumber)
                .retrieve()
                .bodyToMono(Credit.class)
                .map(credit -> credit)
                .switchIfEmpty(Mono.empty());
       
        return result;
       
    }
}
