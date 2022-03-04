package com.nttdata.accounts.dto.request;

import com.nttdata.accounts.entity.Client;
import com.nttdata.accounts.entity.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @NotBlank(message = "Field number must be required")
    private String number;
    private Client client;
    private TypeAccount typeAccount;
    private List<String> holders;
    private List<String> signatories;
    @Min(value = 0, message = "The minimum balance must be 0")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance = BigDecimal.valueOf(0);
    private boolean status = true;
}
