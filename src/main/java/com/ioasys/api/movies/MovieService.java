package com.ioasys.api.movies;

import com.ioasys.api.movies._dtos.DtoPaginator;
import com.ioasys.api.movies._forms.MovieCreateForm;
import com.ioasys.api.movies.vote_history.VoteHistoryRepository;
import com.ioasys.api.movies.vote_history.VoteHistoryService;
import com.ioasys.api.movies.vote_history._forms.VoteHistoryCreateForm;
import com.ioasys.api.shared.exception.ApiException;
import com.ioasys.api.users.UserEntity;
import com.ioasys.api.users.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository repository;

    private final UserRepository userRepository;

    private final VoteHistoryService voteHistoryService;

    private final VoteHistoryRepository voteHistoryRepository;

    public MovieService(
            MovieRepository repository,
            UserRepository userRepository,
            VoteHistoryService voteHistoryService,
            VoteHistoryRepository voteHistoryRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.voteHistoryService = voteHistoryService;
        this.voteHistoryRepository = voteHistoryRepository;
    }

    @Transactional
    public void create(MovieCreateForm form){
        try {

            MovieEntity newMovie = new MovieEntity();
            newMovie.setId(null);
            newMovie.setName(form.getName());
            newMovie.setGenre(form.getGenre());
            newMovie.setDirectedBy(form.getDirectedBy());
            newMovie.setVoteAverage(0);
            newMovie.setTotalVote(0);
            newMovie.setUpdateAt(LocalDateTime.now());
            newMovie.setCreatedAt(LocalDateTime.now());
            repository.save(newMovie);
            return;
        } catch (ApiException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            StringBuilder message = new StringBuilder();
            for(ConstraintViolation c : e.getConstraintViolations()){
                message.append(c.getMessage());
                message.append("\n ");
            }
            throw new ApiException(message.toString());
        } catch (Exception e){
            e.printStackTrace();
            throw new ApiException("Não foi possível criar o Usuário!");
        }
    }

    @Transactional
    public void voteMovie(Long movieId, Long userId, int voteAverage){
        try {
            MovieEntity movie = repository.findById(movieId)
                    .orElseThrow(()->new ApiException("Filme não encontrado !"));
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiException("Usuário não encontrado !"));

            if (voteAverage > 4 || voteAverage < 0)
                throw new ApiException("O Rating de votação para os filmes é de 0 até 4 !");

            voteHistoryService.create(
                    new VoteHistoryCreateForm(
                            user,
                            movie,
                            voteAverage
                    ));

            int sumVotes = voteHistoryRepository.sumTotalVotes(movieId);

            movie.setTotalVote(movie.getTotalVote()+1);
            movie.setVoteAverage(sumVotes / movie.getTotalVote());
            movie.setUpdateAt(LocalDateTime.now());

            repository.save(movie);
            return;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Não foi possivel fazer a votação!");
        }
    }

    public DtoPaginator findAllByParameters(String name, String genre, String directedBy, int page, int quantity){
        try {
            Pageable sort = PageRequest.of(page, quantity, Sort.unsorted());

            Page<MovieEntity> listFounded = repository
                    .findAll(sort);

            DtoPaginator dtoPaginator = new DtoPaginator(
                    listFounded.getTotalPages(),
                    listFounded.getSize(),
                    listFounded.getTotalElements(),
                    listFounded.getContent()
            );

            if(!directedBy.isEmpty()) {
                List<MovieEntity> list = listFounded.getContent()
                                .stream()
                                .filter(item -> item.getDirectedBy()
                                            .toLowerCase()
                                            .contains(directedBy.trim().toLowerCase()))
                                .collect(Collectors.toList());
                dtoPaginator.setContent(list);
            }

            if(!name.isEmpty()) {
                List<MovieEntity> list = listFounded.getContent()
                                .stream()
                                .filter(item -> item.getName()
                                        .toLowerCase()
                                        .contains(name.trim().toLowerCase()))
                                .collect(Collectors.toList());
                dtoPaginator.setContent(list);
            }

            if(!genre.isEmpty()) {
                List<MovieEntity> list = listFounded.getContent()
                        .stream()
                        .filter(item -> item.getGenre()
                                .toLowerCase()
                                .contains(genre.trim().toLowerCase()))
                        .collect(Collectors.toList());
                listFounded.getContent().addAll(list);
                dtoPaginator.setContent(list);
            }
            return dtoPaginator;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Não foi possivel buscar os filmes !");
        }
    }
}
