package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test") // Activates the test profile
@AutoConfigureWebTestClient
class InteractionControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LikeRepository likeRepository;

    private final String BASE_URL = "/api/v1/interactions";

    @BeforeEach
    public void setUp() {
        likeRepository.deleteAll().block();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetLikesByArticle_thenReturnAllLikes() {
        // Arrange
        var articleId = new ArticleIdentifier("article-1");

        var like1 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleId)
                .readerId("reader-001")
                .timestamp(LocalDateTime.now())
                .build();

        var like2 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleId)
                .readerId("reader-002")
                .timestamp(LocalDateTime.now())
                .build();

        var like3 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-2"))
                .readerId("reader-003")
                .timestamp(LocalDateTime.now())
                .build();

        likeRepository.saveAll(List.of(like1, like2, like3)).blockLast();

        String url = BASE_URL + "/likes/article/" + articleId.getArticleId();

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(LikeResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    response.forEach(like -> assertEquals(articleId.getArticleId(), like.getArticleId()));
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetLikesByReader_thenReturnAllLikes() {
        // Arrange
        var readerId = "reader-001";

        var like1 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-1"))
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        var like2 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-2"))
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        var like3 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-3"))
                .readerId("reader-002")
                .timestamp(LocalDateTime.now())
                .build();

        likeRepository.saveAll(List.of(like1, like2, like3)).blockLast();

        String url = BASE_URL + "/likes/reader/" + readerId;

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(LikeResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    response.forEach(like -> assertEquals(readerId, like.getReaderId()));
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenLikeArticle_thenReturnCreatedLike() {
        // Arrange
        var articleId = "article-1";
        var readerId = "reader-001";

        String url = BASE_URL + "/like?articleId=" + articleId + "&readerId=" + readerId;

        // Act & Assert
        webTestClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LikeResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(articleId, response.getArticleId());
                    assertEquals(readerId, response.getReaderId());
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenUnlikeArticle_thenReturnNoContent() {
        // Arrange
        var articleId = new ArticleIdentifier("article-1");
        var readerId = "reader-001";

        var like = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleId)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        likeRepository.save(like).block();

        String url = BASE_URL + "/unlike?articleId=" + articleId.getArticleId() + "&readerId=" + readerId;

        // Act & Assert
        webTestClient.delete()
                .uri(url)
                .exchange()
                .expectStatus().isNoContent();

        var remainingLikes = likeRepository.findByArticleIdentifierAndReaderId(articleId, readerId).block();
        assertNull(remainingLikes, "Like should be deleted");
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetLikeByIdentifier_thenReturnLike() {
        // Arrange
        var articleId = new ArticleIdentifier("article-1");
        var readerId = "reader-001";

        var like = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleId)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        likeRepository.save(like).block();

        String url = BASE_URL + "/likes/" + like.getLikeIdentifier().getLikeId();

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(LikeResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(like.getLikeIdentifier().getLikeId(), response.getLikeId());
                    assertEquals(articleId.getArticleId(), response.getArticleId());
                    assertEquals(readerId, response.getReaderId());
                });
    }
}
