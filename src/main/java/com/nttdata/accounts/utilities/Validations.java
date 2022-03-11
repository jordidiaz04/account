package com.nttdata.accounts.utilities;

import com.nttdata.accounts.entity.Account;
import com.nttdata.accounts.exceptions.customs.CustomInformationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Validation class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
  /**
   * Validate before creating an account.
   *
   * @param count   Number of accounts per type
   * @param account Account object
   */
  public static Long validateCreateAccount(Long count, Account account) {
    if (account.getClient().getType() == Constants.ClientType.PERSONAL && count > 0) {
      throw new
          CustomInformationException("The type of client can only have 1 account of this type");
    } else if (account.getClient().getType() == Constants.ClientType.BUSINESS
        && account.getTypeAccount().getOption() != Constants.AccountType.CHECKING && count > 0) {
      throw new
          CustomInformationException("The type of client can only have multiple current accounts");
    } else if (account.getClient().getType() == Constants.ClientType.BUSINESS
        && (account.getHolders() == null || account.getHolders().isEmpty())) {
      throw new
          CustomInformationException("The account type requires at least one holder");
    } else {
      return count;
    }
  }
}
