package com.ioasys.api.movies._dtos;

import com.ioasys.api.movies.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoPaginator {

    private int totalPages;

    private int size;

    private long totalElements;

    private List<MovieEntity> content;
}
