package com.nttdata.accounts.dto.request;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * Account object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
  @NotBlank(message = "Field number must be required")
  private String number;
  @Valid
  private ClientRequest client;
  @Valid
  private TypeAccountRequest typeAccount;
  private List<String> holders;
  private List<String> signatories;
  @Min(value = 0, message = "The minimum balance must be 0")
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal balance = BigDecimal.valueOf(0);
  private boolean status = true;
}
