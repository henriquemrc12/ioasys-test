package com.ioasys.api.movies.vote_history;

import com.ioasys.api.movies.vote_history._forms.VoteHistoryCreateForm;
import com.ioasys.api.shared.exception.ApiException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Service
public class VoteHistoryService {

    private final VoteHistoryRepository repository;

    public VoteHistoryService(VoteHistoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void create(VoteHistoryCreateForm form){
        try {

            VoteHistoryEntity newVote = new VoteHistoryEntity();
            newVote.setId(null);
            newVote.setAverage(form.getAverage());
            newVote.setMovie(form.getMovie());
            newVote.setUser(form.getUser());
            newVote.setUpdateAt(LocalDateTime.now());
            newVote.setCreatedAt(LocalDateTime.now());

            repository.save(newVote);
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

}
