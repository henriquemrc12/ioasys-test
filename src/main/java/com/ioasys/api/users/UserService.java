package com.ioasys.api.users;

import com.ioasys.api.shared.exception.ApiException;
import com.ioasys.api.users._enums.UserStatus;
import com.ioasys.api.users._enums.UserType;
import com.ioasys.api.users._forms.UserCreateForm;
import com.ioasys.api.users._forms.UserUpdateForm;
import io.swagger.annotations.Api;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder;

    private final UserRepository repository;

    public UserService(BCryptPasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Transactional
    public void create(UserCreateForm form){
        try {

            if (repository.existsByEmail(form.getEmail().trim()))
                throw new ApiException("Já existe um usuário cadastrado com e-mail: "+form.getEmail());

            if (!UserType.contains(form.getUserType().trim().toUpperCase()))
                throw new ApiException("Tipo de Usuário Inválido");

            UserEntity newUser = new UserEntity();
            newUser.setName(form.getName());
            newUser.setEmail(form.getEmail().trim());
            newUser.setPassword(encoder.encode(form.getPassword().trim()));
            newUser.setUserType(UserType.valueOf(form.getUserType().trim().toUpperCase()));
            newUser.setUserStatus(UserStatus.ABLE);
            newUser.setUpdateAt(LocalDateTime.now());
            newUser.setCreatedAt(LocalDateTime.now());

            repository.save(newUser);
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
    public void update(UserUpdateForm form){
        try {
            if (!UserType.contains(form.getUserType().trim().toUpperCase()))
                throw new ApiException("Tipo de Usuário Inválido");

            UserEntity userFounded = repository.findById(form.getId())
                    .orElseThrow(() -> new ApiException("Usuário não encontrado!"));

            if (repository.existsByEmail(form.getEmail().trim()) &&
                    (!form.getEmail().equalsIgnoreCase(userFounded.getEmail())))
                throw new ApiException("Já existe um usuário cadastrado com e-mail: "+form.getEmail());

            userFounded.setName(form.getName());
            userFounded.setEmail(form.getEmail().trim());
            userFounded.setUpdateAt(LocalDateTime.now());

            repository.save(userFounded);
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
            throw new ApiException("Não foi possível atualizar o Usuário!");
        }
    }

    @Transactional
    public void delete(Long id){
        try {

            UserEntity userFounded = repository.findById(id)
                    .orElseThrow(()-> new ApiException("Usuário não encontrado!"));

            userFounded.setUserStatus(UserStatus.DISABLE);
            repository.save(userFounded);

            return;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new ApiException("Não foi possível desativar o Usuário!");
        }
    }

    public List<UserEntity> findAllByUserTypeAndStatus(String userType,String status){
        try {
            if (!UserType.contains(userType.trim().toUpperCase()))
                throw new ApiException("Tipo de Usuário Inválido");

            if (!UserStatus.contains(status.trim().toUpperCase()))
                throw new ApiException("Status de Usuário Inválido");

            return repository.findAllByUserTypeAndUserStatus(
                    UserType.valueOf(userType.trim().toUpperCase()),
                            UserStatus.valueOf(status.trim().toUpperCase()))
                    .orElse(new ArrayList<>());
        } catch (ApiException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new ApiException("Não foi possível buscar os Usuários!");
        }
    }

    public UserEntity findByEmail(String email){
        try {
            return repository.findByEmail(email)
                    .orElseThrow(()-> new ApiException("Usuário não encontrado !"));
        } catch (ApiException e) {
            throw e;
        } catch (Exception e){
            throw new ApiException("Não foi possível buscar o usuário!");
        }
    }
}
