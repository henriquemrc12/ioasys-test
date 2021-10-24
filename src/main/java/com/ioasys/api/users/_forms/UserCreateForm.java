package com.ioasys.api.users._forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateForm {

    private String name;

    private String email;

    private String password;

    private String userType;
}
