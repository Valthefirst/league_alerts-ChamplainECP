package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        // Clean up the repository before each test to ensure isolation
        articleRepository.deleteAll().block();

        // Add sample data for testing
        var article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        var article2 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 2")
                .body("This is the body of article 2")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        var article3 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Article 3")
                .body("This is the body of article 3")
                .wordCount(7)
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NFL")
                .timePosted(LocalDateTime.now())
                .build();

        articleRepository.saveAll(List.of(article1, article2, article3)).blockLast();
    }

    @Test
    public void whenArticleIdIsValid_thenReturnArticle() {
        // Arrange
        var savedArticle = articleRepository.findAll().blockFirst();
        assertNotNull(savedArticle, "Saved article should not be null");

        var articleId = savedArticle.getArticleIdentifier().getArticleId();

        // Act
        var actualArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();

        // Assert
        assertNotNull(actualArticle, "Retrieved article should not be null");
        assertEquals(articleId, actualArticle.getArticleIdentifier().getArticleId(), "Article ID should match");
    }

//    @Test
//    public void whenArticleTagIsValid_thenReturnAllArticles() {
//        // Arrange
//        var savedArticles = articleRepository.findAll().collectList().block();
//        assertNotNull(savedArticles, "Saved articles should not be null");
//
//        var tag = savedArticles.get(0).getTags();
//
//        // Act
//        var actualArticles = articleRepository.findAllArticleByTags(tag).collectList().block();
//
//        // Assert
//        assertNotNull(actualArticles, "Retrieved articles should not be null");
//        assertTrue(actualArticles.size() > 0, "Retrieved articles should not be empty");
//        actualArticles.forEach(article -> assertEquals(tag, article.getTags(), "Article tag should match"));
//     }
}
