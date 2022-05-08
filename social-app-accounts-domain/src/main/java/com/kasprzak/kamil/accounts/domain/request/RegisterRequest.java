package com.kasprzak.kamil.accounts.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class RegisterRequest {

    private String username;

    private String password;

}
