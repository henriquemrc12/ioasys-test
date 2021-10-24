package com.ioasys.api.movies;

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
@Table(name = "TB_MOVIES")
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome não pode ser Nulo!")
    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotNull(message = "O Genero não pode ser Nulo!")
    @NotEmpty(message = "O Genero não pode ser vazio!")
    private String genre;

    @NotNull(message = "O Diretor não não pode ser Nulo!")
    @NotEmpty(message = "O Diretor não pode ser vazio!")
    @Column(name = "directed_by")
    private String directedBy;

    @Column(name = "vote_average")
    private float voteAverage;

    @Column(name = "total_vote")
    private int totalVote;

    @Column(name="update_at")
    private LocalDateTime updateAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
