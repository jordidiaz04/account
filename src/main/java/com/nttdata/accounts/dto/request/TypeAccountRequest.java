package com.nttdata.accounts.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeAccountRequest {
    @NotNull(message = "Field option for typeAccount must be required")
    private Integer option;
    private Integer maxTransactions;
    private BigDecimal maintenanceFee;
    @NotNull(message = "Field tax for typeAccount must be required")
    private BigDecimal tax;
    private Integer day;
}
