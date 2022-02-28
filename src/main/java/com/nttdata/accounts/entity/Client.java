package com.nttdata.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Integer id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private int type;
    private boolean active;
}