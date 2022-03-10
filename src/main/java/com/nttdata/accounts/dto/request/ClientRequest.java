package com.nttdata.accounts.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
  @NotBlank(message = "Field id for client must be required")
  private String id;
  @NotBlank(message = "Field firstName for client must be required")
  private String firstName;
  @NotBlank(message = "Field lastName for client must be required")
  private String lastName;
  @NotBlank(message = "Field documentNumber for client must be required")
  private String documentNumber;
  @NotNull(message = "Field type for client must be required")
  private Integer type;
  @NotNull(message = "Field profile for client must be required")
  private Integer profile;
}
