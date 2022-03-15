package com.nttdata.accounts.utilities;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Validation class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
  /**
   * Validate fields of account.
   *
   * @param account Account object
   */
  public static Mono<Account> validateFields(Account account) {
    if (account.getTypeAccount().getOption() == Constants.AccountType.SAVING) {
      validateForSavingAccount(account);
    } else if (account.getTypeAccount().getOption() == Constants.AccountType.CHECKING) {
      validateForCheckingAccount(account);
    } else {
      validateForFixedTermAccount(account);
    }

    return Mono.just(account);
  }

  /**
   * Validate before creating an account.
   *
   * @param count   Number of accounts per type
   * @param account Account object
   */
  public static Mono<Account> validateCreateAccount(Long count, Account account) {
    if (account.getClient().getType() == Constants.ClientType.PERSONAL && count > 0) {
      throw new
          CustomInformationException("The type of client can only have 1 account of this type");
    } else if (account.getClient().getType() == Constants.ClientType.BUSINESS
        && account.getTypeAccount().getOption() != Constants.AccountType.CHECKING) {
      throw new
          CustomInformationException("The type of client can only have multiple current accounts");
    } else if (account.getClient().getType() == Constants.ClientType.BUSINESS
        && (account.getHolders() == null || account.getHolders().isEmpty())) {
      throw new
          CustomInformationException("The account type requires at least one holder");
    }
    return Mono.just(account);
  }

  private static void validateForSavingAccount(Account account) {
    if (account.getTypeAccount().getMaxTransactions() == null) {
      throw new CustomInformationException("Field maxTransactions for "
          + "typeAccount must be required");
    } else if (account.getTypeAccount().getMaxTransactions() == 0) {
      throw new CustomInformationException("Field maxTransactions for "
          + "typeAccount must be at least 1");
    }
    account.getTypeAccount().setMaintenance(null);
  }

  private static void validateForCheckingAccount(Account account) {
    if (account.getClient().getProfile() == Constants.ClientProfile.PYME) {
      account.getTypeAccount().setMaintenance(null);
    } else {
      if (account.getTypeAccount().getMaintenance() == null) {
        throw new CustomInformationException("Field maintenance for typeAccount "
            + "must be required");
      } else if (account.getTypeAccount().getMaintenance().compareTo(BigDecimal.ZERO) <= 0) {
        throw new CustomInformationException("Field maintenance for typeAccount "
            + "must be greater than 0");
      }
    }
    account.getTypeAccount().setMaxTransactions(null);
  }

  private static void validateForFixedTermAccount(Account account) {
    if (account.getTypeAccount().getDay() == null) {
      throw new CustomInformationException("Field day for typeAccount must be required");
    } else {
      int lastDayOfMonth = LocalDate
          .now()
          .withDayOfMonth(LocalDate.now().lengthOfMonth()).getDayOfMonth();
      if (account.getTypeAccount().getDay() <= 0
          || account.getTypeAccount().getDay() > lastDayOfMonth) {
        throw new CustomInformationException("Field day for typeAccount "
            + "must be between 1 and " + lastDayOfMonth);
      }
    }
    account.getTypeAccount().setMaintenance(null);
    account.getTypeAccount().setMaxTransactions(1);
  }
}
