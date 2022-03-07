package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.accounts.dto.request.TypeAccountRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeAccount {
    private int option;
    private Integer maxTransactions;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maintenanceFee;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal tax;
    private Integer day;

    public TypeAccount(TypeAccountRequest request) {
        option = request.getOption();
        maxTransactions = request.getMaxTransactions();
        maintenanceFee = request.getMaintenanceFee();
        tax = request.getTax();
        day = request.getDay();
    }
}
