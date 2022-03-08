package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.accounts.dto.request.AccountRequest;
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

@Document("accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String number;
    private Client client;
    private TypeAccount typeAccount;
    private List<String> holders;
    private List<String> signatories;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance = BigDecimal.valueOf(0);
    private boolean status = true;

    public Account(AccountRequest request) {
        number = request.getNumber();
        client = new Client(request.getClient());
        typeAccount = new TypeAccount(request.getTypeAccount());
        holders = request.getHolders();
        signatories = request.getSignatories();
        balance = request.getBalance();
    }
}
