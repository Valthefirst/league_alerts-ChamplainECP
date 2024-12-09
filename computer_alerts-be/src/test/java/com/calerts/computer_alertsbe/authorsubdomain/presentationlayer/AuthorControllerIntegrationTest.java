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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port=0"})
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class AuthorControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;

    private Author author2;

    @BeforeEach
    void setUpDB() {
        authorRepository.deleteAll().block();

        author1 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("3e91879e-7fc2-4107-9f0f-17e33f67e94e"))
                .emailAddress("variable.x@email.com")
                .firstName("X")
                .lastName("Variable")
                .biography(new Biography("Lorem Ipsum.", 2))
                .build();

        author2 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("ac919079-e593-49c5-881c-057a29765bbd"))
                .emailAddress("variable.y@email.com")
                .firstName("Y")
                .lastName("Variable")
                .biography(new Biography())
                .build();

        authorRepository.saveAll(Flux.just(author1, author2)).blockLast();

    }

    // Positive test case
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void whenGetAllAuthors_thenReturnAuthors() {
        StepVerifier.create(authorRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
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
    @WithMockUser(username = "testuser", roles = {"USER"})
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
                .expectHeader().contentType("application/json")
                .expectBodyList(AuthorResponseModel.class)
                .value(authorResponseModels -> {
                    assertNotNull(authorResponseModels);
                    assertEquals(0, authorResponseModels.size());
                });
    }

    // Positive test case
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void whenGetAuthorById_thenReturnAuthor() {
        StepVerifier.create(authorRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

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
    @WithMockUser(username = "testuser", roles = {"USER"})
    void whenInvalidAuthorId_thenReturnBadRequest() {
        StepVerifier.create(authorRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/api/v1/authors/invalid-author-id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
//                .value(response -> assertEquals("Provided author id is invalid: invalid-author-id", response));
                .jsonPath("$.message").isEqualTo("Provided author id is invalid: invalid-author-id");
    }

    // Negative test case
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void whenAuthorNotFound_thenReturnNotFound() {
        StepVerifier.create(authorRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/api/v1/authors/3e91879e-7fc2-4107-9f0f-17e33f67e94f")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
//                .value(response -> assertEquals("Author id not found: 3e91879e-7fc2-4107-9f0f-17e33f67e94f", response));
                .jsonPath("$.message").isEqualTo("Author id not found: 3e91879e-7fc2-4107-9f0f-17e33f67e94f");
    }
}