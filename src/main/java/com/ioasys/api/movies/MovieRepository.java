package com.ioasys.api.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query(value = "SELECT * FROM tb_movies e WHERE e.name =:name OR e.genre =:genre OR e.directed_by =:directedBy", nativeQuery = true)
    List<MovieEntity> findAllByParameters(String name, String genre, String directedBy);
}
