package com.calerts.computer_alertsbe.authorsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.Author;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorRepository;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.Biography;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port=0"})
//@TestPropertySource(properties = {
//        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
//                "org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration"
//})
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class AuthorControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1 = Author.builder()
            .authorIdentifier(new AuthorIdentifier("3e91879e-7fc2-4107-9f0f-17e33f67e94e"))
            .emailAddress("variable.x@email.com")
            .firstName("X")
            .lastName("Variable")
            .biography(new Biography("Lorem Ipsum."))
            .build();

    private Author author2 = Author.builder()
            .authorIdentifier(new AuthorIdentifier("ac919079-e593-49c5-881c-057a29765bbd"))
            .emailAddress("variable.y@email.com")
            .firstName("Y")
            .lastName("Variable")
            .biography(new Biography())
            .build();

    @BeforeEach
    void setUpDB() {
        Publisher<Author> setupDB = authorRepository.deleteAll()
                .thenMany(Flux.just(author1, author2)
                        .flatMap(authorRepository::save));

        StepVerifier.create(setupDB)
                .expectNext(author1)
                .expectNext(author2)
                .expectNextCount(0)
                .verifyComplete();
    }

    // Positive test case
    @Test
    void whenGetAllAuthors_thenReturnAuthors() {
        webTestClient
                .get()
                .uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=UTF-8")
                .expectBodyList(AuthorResponseModel.class)
                .hasSize(2)
                .value(authorResponseModels -> {
                    assertEquals(author1.getAuthorIdentifier().getAuthorId(), authorResponseModels.get(0).getAuthorId());
                    assertEquals(author1.getEmailAddress(), authorResponseModels.get(0).getEmailAddress());
                    assertEquals(author1.getFirstName(), authorResponseModels.get(0).getFirstName());
                    assertEquals(author1.getLastName(), authorResponseModels.get(0).getLastName());
                    assertEquals(author1.getBiography().getBiography(), authorResponseModels.get(0).getBiography());

                    assertEquals(author2.getAuthorIdentifier().getAuthorId(), authorResponseModels.get(1).getAuthorId());
                    assertEquals(author2.getEmailAddress(), authorResponseModels.get(1).getEmailAddress());
                    assertEquals(author2.getFirstName(), authorResponseModels.get(1).getFirstName());
                    assertEquals(author2.getLastName(), authorResponseModels.get(1).getLastName());
                    assertEquals(author2.getBiography().getBiography(), authorResponseModels.get(1).getBiography());
                });
    }

    // Negative test case
    @Test
    void whenNoAuthorsExist_thenReturnEmptyList() {
        Publisher<Void> deleteAllAuthors = authorRepository.deleteAll();

        StepVerifier.create(deleteAllAuthors)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=UTF-8")
                .expectBodyList(AuthorResponseModel.class)
                .value(authorResponseModels -> {
                    assertNotNull(authorResponseModels);
                    assertEquals(0, authorResponseModels.size());
                });
    }

    // Positive test case
    @Test
    void whenGetAuthorById_thenReturnAuthor() {
        webTestClient
                .get()
                .uri("/api/v1/authors/" + author1.getAuthorIdentifier().getAuthorId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorResponseModel.class)
                .value(authorResponseModel -> {
                    assertEquals(author1.getAuthorIdentifier().getAuthorId(), authorResponseModel.getAuthorId());
                    assertEquals(author1.getEmailAddress(), authorResponseModel.getEmailAddress());
                    assertEquals(author1.getFirstName(), authorResponseModel.getFirstName());
                    assertEquals(author1.getLastName(), authorResponseModel.getLastName());
                    assertEquals(author1.getBiography().getBiography(), authorResponseModel.getBiography());
                });
    }

    // Negative test case
    @Test
    void whenInvalidAuthorId_thenReturnBadRequest() {
        webTestClient
                .get()
                .uri("/api/v1/authors/invalid-author-id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(response -> assertEquals("Provided author id is invalid: invalid-author-id", response));
    }

    // Negative test case
    @Test
    void whenAuthorNotFound_thenReturnNotFound() {
        webTestClient
                .get()
                .uri("/api/v1/authors/3e91879e-7fc2-4107-9f0f-17e33f67e94f")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .value(response -> assertEquals("Author id not found: 3e91879e-7fc2-4107-9f0f-17e33f67e94f", response));
    }
}