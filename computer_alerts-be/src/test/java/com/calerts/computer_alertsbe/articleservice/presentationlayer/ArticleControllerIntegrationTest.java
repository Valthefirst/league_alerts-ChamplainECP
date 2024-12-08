package com.calerts.computer_alertsbe.articleservice.presentationlayer;

import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ArticleControllerIntegrationTest {
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    private final String BASE_URL = "/api/v1/articles";
//
//    @BeforeEach
//    public void setUp() {
//        articleRepository.deleteAll();
//    }
//
//    @Test
//    public void whenGetArticleById_thenReturnArticle() {
//        // Arrange
//        var article1 = Article.builder()
//                .articleIdentifier(new ArticleIdentifier())
//                .title("Article 1")
//                .body("This is the body of article 1")
//                .wordCount(7)
//                .articleStatus(ArticleStatus.PUBLISHED)
//                .tags("NBA")
//                .timePosted(LocalDateTime.now())
//                .build();
//
//        articleRepository.save(article1);
//
//        String url = BASE_URL + "/" + article1.getArticleIdentifier().getArticleId();
//
//        // Act & Assert
//        webTestClient.get()
//                .uri(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(ArticleResponseModel.class)
//                .value((response) -> {
//                    assertNotNull(response);
//                    assertEquals(article1.getArticleIdentifier().getArticleId(), response.getArticleId());
//                    assertEquals(article1.getTitle(), response.getTitle());
//                    assertEquals(article1.getBody(), response.getBody());
//                    assertEquals(article1.getWordCount(), response.getWordCount());
//                    assertEquals(article1.getArticleStatus().toString(), response.getArticleStatus());
//                    assertEquals(article1.getTags(), response.getTags());
//                });
//    }
}

