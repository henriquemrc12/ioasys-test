package com.ioasys.api.movies.vote_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteHistoryRepository extends JpaRepository<VoteHistoryEntity, Long> {

    @Query(value = "SELECT SUM(v.average) FROM VoteHistoryEntity v WHERE v.movie.id=:movieId")
    int sumTotalVotes(Long movieId);
}
