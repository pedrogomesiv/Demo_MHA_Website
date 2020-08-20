package com.nsa.mhasite.domain;

import lombok.Data;

@Data
public class NewPasswordForm {
    private String password;
    private String new_password1;
    private String new_password2;
}
