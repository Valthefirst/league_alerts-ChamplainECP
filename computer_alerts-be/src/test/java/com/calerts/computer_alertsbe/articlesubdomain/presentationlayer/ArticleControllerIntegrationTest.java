package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.*;
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
import java.time.ZonedDateTime;
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
        articleRepository.deleteAll().block();
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944091/pexels-introspectivedsgn-7783413_r7s5xx.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
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
    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void whenCreateValidArticle_thenReturnCreatedArticle() {
        // Arrange
        ArticleRequestModel articleRequestModel = ArticleRequestModel.builder()
                .title("Test Article")
                .body("This is a detailed test article with sufficient word count to pass validation.")
                .wordCount(120)
                .tags("NBA")
                .build();

        // Act & Assert
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(articleRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ArticleResponseModel.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(articleRequestModel.getTitle(), response.getTitle());
                    assertEquals(ArticleStatus.ARTICLE_REVIEW, response.getArticleStatus());
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void whenCreateArticleWithInvalidData_thenReturnBadRequest() {
        // Arrange
        ArticleRequestModel invalidArticleRequest = ArticleRequestModel.builder()
                .title("")
                .body("Short body")
                .wordCount(50)
                .build();

        // Act & Assert
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidArticleRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void whenAcceptValidArticle_thenReturnNoContent() {
        // Arrange
        var article = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article to Accept")
                .body("This is the body of an article pending review")
                .wordCount(100)
                .articleStatus(ArticleStatus.ARTICLE_REVIEW)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        // Save the article first
        articleRepository.save(article).block();

        String url = BASE_URL + "/acceptArticle/" + article.getArticleIdentifier().getArticleId();

        // Act & Assert
        webTestClient.patch()
                .uri(url)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void whenAcceptNonExistentArticle_thenReturnNotFound() {
        // Arrange
        String invalidId = "a0466beb-a91c-4022-a58d-765bb1bbade3";
        String url = BASE_URL + "/acceptArticle/" + invalidId;

        // Act & Assert
        webTestClient.patch()
                .uri(url)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testSearchArticlesByTagAndTitle() {

        //arrange
        var article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944091/pexels-introspectivedsgn-7783413_r7s5xx.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        articleRepository.saveAll(List.of(article1, article2, article3)).blockLast();

        String url = BASE_URL + "/tag/NBA/search?query=Article";

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

}