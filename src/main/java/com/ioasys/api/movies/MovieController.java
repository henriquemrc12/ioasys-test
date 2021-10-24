package com.ioasys.api.movies;

import com.ioasys.api.movies._forms.MovieCreateForm;
import com.ioasys.api.users.UserService;
import com.ioasys.api.users._forms.UserCreateForm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service){
        this.service = service;
    }

    @ApiOperation(value = "Cria um novo filme.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Filme criado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MovieCreateForm form) {
        service.create(form);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Vota no filme.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Voto cadastrado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{movieId}/user/{userId}/vote/{voteAverage}")
    public ResponseEntity<?> voteMovie(
            @PathVariable Long movieId,
            @PathVariable Long userId,
            @PathVariable int voteAverage) {
        service.voteMovie(movieId, userId, voteAverage);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @ApiOperation(value = "Retorna os filmes por parametros")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Voto cadastrado com sucesso"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar esse ENDPOINT"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<?> findAllByParameters(
            @RequestParam(
                    name = "name",
                    required = false,
                    defaultValue = ""
            ) String name,
            @RequestParam(
                    name = "genre",
                    required = false,
                    defaultValue = ""
            ) String genre,
            @RequestParam(
                    name = "directedBy",
                    required = false,
                    defaultValue = ""
            ) String directedBy,
            @RequestParam(
                    name = "page",
                    required = false,
                    defaultValue = "0"
            ) int page,
            @RequestParam(
                    name = "quantity",
                    required = false,
                    defaultValue = "10"
            ) int quantity
    ) {
        return ResponseEntity.ok().body(service.findAllByParameters(name, genre, directedBy, page, quantity));
    }
}
