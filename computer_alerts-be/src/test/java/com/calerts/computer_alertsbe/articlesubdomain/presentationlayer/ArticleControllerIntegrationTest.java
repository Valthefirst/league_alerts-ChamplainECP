package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
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
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ArticleControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ArticleRepository articleRepository;

    private final String BASE_URL = "/api/v1/articles";

    @BeforeEach
    public void setUp() {
        articleRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetAllArticlesByTag_thenReturnAllArticles() {

        articleRepository.deleteAll().block();

        var article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .build();

        var article2 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 2")
                .body("This is the body of article 2")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .build();

        var article3 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 3")
                .body("This is the body of article 3")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NFL")
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .build();

        articleRepository.saveAll(List.of(article1, article2, article3)).blockLast();

        String url = BASE_URL + "/tag/NBA";

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ArticleResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    response.forEach(article -> assertTrue(article.getTags().contains("NBA")));
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetArticleById_thenReturnArticle() {
        // Arrange
        var article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .build();

        articleRepository.findArticleByArticleIdentifier_ArticleId(article1.getArticleIdentifier().getArticleId())
                .switchIfEmpty(articleRepository.save(article1))
                .block();

        String url = BASE_URL + "/" + article1.getArticleIdentifier().getArticleId();

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ArticleResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(article1.getArticleIdentifier().getArticleId(), response.getArticleId());
                    assertEquals(article1.getTitle(), response.getTitle());
                    assertEquals(article1.getBody(), response.getBody());
                    assertEquals(article1.getWordCount(), response.getWordCount());
                    assertEquals(article1.getArticleStatus(), response.getArticleStatus()); // Compare the enum directly
                    assertEquals(article1.getTags(), response.getTags());
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetArticle_WithNonexistentId_thenReturnNotFound() {
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
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenGetArticle_WithInvalidId_thenReturnUnprocessableEntity() {
        // Arrange
        String invalidId = "a0466beb-a91c-4022-a58d-765bb1bbade3dadsawads";
        String url = BASE_URL + "/" + invalidId;

        // Act & Assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testIncrementRequestCount_ArticleFound() {
        var article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .requestCount(0)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();
        articleRepository.findArticleByArticleIdentifier_ArticleId(article1.getArticleIdentifier().getArticleId())
                .switchIfEmpty(articleRepository.save(article1))
                .block();

        String url = BASE_URL + "/" + article1.getArticleIdentifier().getArticleId();

        webTestClient
                .patch()
                .uri(url)
                .exchange()
                .expectStatus().isNoContent();
    }



    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testIncrementRequestCount_ArticleNotFound() {

        String invalidId = "a0466beb-a91c-4022-a58d-765bb1bbade3";
        String url = BASE_URL + "/" + invalidId;


        webTestClient
                .patch()
                .uri(url)
                .exchange()
                .expectStatus().isNotFound();
    }





}