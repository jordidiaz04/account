package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Document("account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    @Id
    private ObjectId id;
    private String number;
    private Client client;
    private TypeAccount typeAccount;
    private List<String> holders;
    private List<String> signatories;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance = BigDecimal.valueOf(0);
    private boolean status = true;
}
