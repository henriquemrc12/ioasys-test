package com.ioasys.api.movies.vote_history._forms;


import com.ioasys.api.movies.MovieEntity;
import com.ioasys.api.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteHistoryCreateForm {

    private UserEntity user;

    private MovieEntity movie;

    private int average;
}
