package com.nttdata.accounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.accounts.dto.request.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private int type;
    private int profile;

    public Client(ClientRequest request) {
        id = new ObjectId(request.getId());
        firstName = request.getFirstName();
        lastName = request.getLastName();
        documentNumber = request.getDocumentNumber();
        type = request.getType();
        profile = request.getProfile();
    }
}
