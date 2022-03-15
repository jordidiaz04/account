package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.accounts.dto.request.AccountRequest;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * Account object.
 */
@Document("accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String debitCard;
  private String number;
  private int position;
  private Client client;
  private TypeAccount typeAccount;
  private List<String> holders;
  private List<String> signatories;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal balance;
  private boolean status;

  /**
   * Return account from an AccountRequest.
   *
   * @param request AccountRequest object
   */
  public Account(AccountRequest request) {
    debitCard = request.getDebitCard();
    number = request.getNumber();
    position = 1;
    client = new Client(request.getClient());
    typeAccount = new TypeAccount(request.getTypeAccount());
    holders = request.getHolders();
    signatories = request.getSignatories();
    balance = request.getBalance();
    status = true;
  }
}
