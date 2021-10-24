package com.ioasys.api.users;

import com.ioasys.api.users._forms.UserCreateForm;
import com.ioasys.api.users._forms.UserUpdateForm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;


    public UserController(UserService service){
        this.service = service;
    }

    @ApiOperation(value = "Cria um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/{userType}")
    public ResponseEntity<?> create(@RequestBody UserCreateForm form, @PathVariable String userType) {
        form.setUserType(userType);
        service.create(form);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualiza o usuário.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário atualizado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserUpdateForm form) {
        service.update(form);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Exclusão do usuário.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário excluído/desativado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca todos os usuário por Status e Tipo.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "usuário retornado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/all/{status}/{userType}")
    public ResponseEntity<?> findAllByUserTypeAndStatus(@PathVariable String userType, @PathVariable String status) {
        return ResponseEntity.ok().body(service.findAllByUserTypeAndStatus(userType, status));
    }

    @ApiOperation(value = "Busca os detalhes do Usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário logado retornado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/user-logged/details")
    public ResponseEntity<?> findUserLoggedDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok().body(service.findByEmail(userDetails.getUsername()));
    }
}
