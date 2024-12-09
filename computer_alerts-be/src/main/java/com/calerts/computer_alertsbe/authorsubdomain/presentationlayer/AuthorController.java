package com.calerts.computer_alertsbe.authorsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.businesslayer.AuthorService;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AuthorResponseModel> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthorResponseModel>> getAuthor(@PathVariable String authorId) {
        return Mono.just(authorId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided author id is invalid: " + authorId)))
                .flatMap(authorService::getAuthor)
                .map(ResponseEntity::ok);
    }
}
