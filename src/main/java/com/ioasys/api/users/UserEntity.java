package com.ioasys.api.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ioasys.api.users._enums.UserStatus;
import com.ioasys.api.users._enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "TB_USERS")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome não pode ser Nulo!")
    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotNull(message = "O e-mail não pode ser Nulo!")
    @NotEmpty(message = "O e-mail nome não pode ser vazio!")
    @Email(message = "O e-mail é inválido!")
    private String email;

    @NotNull(message = "A senha não pode ser Nula!")
    @NotEmpty(message = "A senha não pode ser vazia!")
    @Size(min = 8, message = "A senha deve possuir no mínimo 8 caracteres!")
    @JsonIgnore
    private String password;

    @Column(name = "user_type")
    @JsonIgnore
    private UserType userType;

    @Column(name = "user_status")
    @JsonIgnore
    private UserStatus userStatus;

    @Column(name="update_at")
    private LocalDateTime updateAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
