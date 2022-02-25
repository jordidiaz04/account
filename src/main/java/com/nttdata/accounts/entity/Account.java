package com.nttdata.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("account")
public class Account {
    private Integer id;
    private Client client;
    private String number;
    private TypeAccount typeAccount;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance = BigDecimal.valueOf(0);
    private boolean status = true;
}
