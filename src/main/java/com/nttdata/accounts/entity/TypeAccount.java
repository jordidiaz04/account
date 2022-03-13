package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.accounts.dto.request.TypeAccountRequest;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * TypeAccount object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeAccount {
  private int option;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal maintenance;
  private Integer maxTransactions;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal commission;
  private Integer day;

  /**
   * Return typeAccount from an TypeAccountRequest.
   *
   * @param request TypeAccountRequest object
   */
  public TypeAccount(TypeAccountRequest request) {
    option = request.getOption();
    maxTransactions = request.getMaxTransactions();
    maintenance = request.getMaintenance();
    commission = request.getCommission();
    day = request.getDay();
  }
}
