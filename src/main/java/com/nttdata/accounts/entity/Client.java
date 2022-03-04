package com.nttdata.accounts.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private int type;
    private int profile;
}
