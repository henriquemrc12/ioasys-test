package com.ioasys.api.movies.vote_history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ioasys.api.movies.MovieEntity;
import com.ioasys.api.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "TB_VOTE_HISTORY")
public class VoteHistoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private MovieEntity movie;

    private int average;

    @Column(name="update_at")
    private LocalDateTime updateAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
