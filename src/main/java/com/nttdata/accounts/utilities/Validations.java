package com.nttdata.accounts.utilities;

import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.accounts.entity.Account;
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
        } else if(account.getClient().getType() == 1 && account.getClient().getProfile() == 2) {
        	//validateClientOwnsCreditCard(account.getClient().getDocumentNumber());
        	throw new CustomInformationException("The account type requires that the client owns a credit card");
        				
        } else if(account.getClient().getType() == 2 && account.getClient().getProfile() == 3) {
        	/*validateClientOwnsCreditCard(account.getClient().getDocumentNumber())
        	.map(result -> {
        		if(result == 0)
        			throw new CustomInformationException("The account type requires that the client owns a credit card");
        		else
        			return result;
        			
        	});*/
        	throw new CustomInformationException("The account type requires that the client owns a credit card");
       
        } else {
            return count;
        }
    }
    
    @SuppressWarnings("unchecked")
	private static Mono<Long> validateClientOwnsCreditCard( String documentNumber) { 
    	
    	String path = "http://localhost:80959/credits";
        String url = path + "clientOwnsCard/{documentNumber}";
        
        Mono<Long> result = (Mono<Long>) webClient.get()
                .uri(url, documentNumber)
                .retrieve()
                .bodyToMono(Long.class)
                .subscribe();
        
        /*return result.flatMap( data -> {
        	if(data == 0)
        		return false;
        	else 
        		return true;
        })*/
        
        return result;
        
        
    	
    }
}
