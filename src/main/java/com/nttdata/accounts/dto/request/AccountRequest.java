package com.nttdata.accounts.dto.request;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
  @NotBlank(message = "Field debitCard must be required")
  private String debitCard;
  @NotBlank(message = "Field number must be required")
  private String number;
  @Valid
  private ClientRequest client;
  @Valid
  private TypeAccountRequest typeAccount;
  private List<String> holders;
  private List<String> signatories;
  @NotNull(message = "Field balance must be required")
  @Min(value = 0, message = "The minimum balance must be 0")
  private BigDecimal balance;
  private boolean status = true;
}
