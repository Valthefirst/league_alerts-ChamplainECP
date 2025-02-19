package com.calerts.computer_alertsbe.readersubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderIdentifier;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ReaderControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReaderRepository readerRepository;

    private final String BASE_URL = "/api/v1/readers";

    @BeforeEach
    public void setUp() {
        readerRepository.deleteAll().block();
    }




    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetReaderById_thenReturnReader() {
        // Arrange
        var reader1 = Reader.builder()
                .readerIdentifier(new ReaderIdentifier())
                .auth0userId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .build();

        readerRepository.findReaderByReaderIdentifier_ReaderId(reader1.getReaderIdentifier().getReaderId())
                .switchIfEmpty(readerRepository.save(reader1))
                .block();

        String url = BASE_URL + "/" + reader1.getAuth0userId();

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ReaderResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(reader1.getAuth0userId(), "22222");

                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetReader_WithNonexistentAuth0UserId_thenReturnNotFound() {
        // Arrange
        String invalidId = "a0466beb-a91c-4022-a58d-765bb1bbade3";
        String url = BASE_URL + "/" + invalidId;

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void whenUpdatingReaderWithValidId_ReturnUpdatedReader(){
        // Arrange
        var reader1 = Reader.builder()
                .readerIdentifier(new ReaderIdentifier())
                .auth0userId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .build();

        // Save the reader first
        readerRepository.save(reader1).block();

        String url = BASE_URL + "/" + reader1.getAuth0userId();

        ReaderRequestModel updatedReader = ReaderRequestModel.builder()

                .readerId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .build();

        // Act & Assert
        webTestClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedReader)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ReaderResponseModel.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(updatedReader.getEmailAddress(), response.getEmailAddress());

                });
    }

    @Test
    void whenUpdatingReaderWithInvalidId_ReturnNotFound(){
        // Arrange
        var reader1 = Reader.builder()
                .readerIdentifier(new ReaderIdentifier())
                .auth0userId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .build();

        // Save the reader first
        readerRepository.save(reader1).block();

        String url = BASE_URL + "/" + "a0466beb-a91c-4022-a58d-765bb1bbade3";

        ReaderRequestModel updatedReader = ReaderRequestModel.builder()

                .readerId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .build();

        // Act & Assert
        webTestClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedReader)
                .exchange()
                .expectStatus().isNotFound();

    }







}