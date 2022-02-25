package com.nttdata.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeAccount {
    private int option;
    private Integer maxMoves;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maintenanceFee;
    private int day;
}
