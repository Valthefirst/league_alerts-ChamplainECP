package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.articlesubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
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
                .likeCount(0)
                .timePosted(LocalDateTime.now())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
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
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944091/pexels-introspectivedsgn-7783413_r7s5xx.jpg")
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

    @Test
    public void whenArticleTagIsValid_thenReturnAllArticles() {
        // Arrange
        var savedArticles = articleRepository.findAll().collectList().block();
        assertNotNull(savedArticles, "Saved articles should not be null");

        var tag = savedArticles.get(0).getTags();

        // Act
        var actualArticles = articleRepository.findAllArticleByTags(tag).collectList().block();

        // Assert
        assertNotNull(actualArticles, "Retrieved articles should not be null");
        assertTrue(actualArticles.size() > 0, "Retrieved articles should not be empty");
        actualArticles.forEach(article -> assertEquals(tag, article.getTags(), "Article tag should match"));
    }

    @Test
    public void whenQueryIsValid_thenReturnMatchingArticles() {
        // Arrange
        var savedArticles = articleRepository.findAll().collectList().block();
        assertNotNull(savedArticles, "Saved articles should not be null");

        var tag = savedArticles.get(0).getTags();
        var query = savedArticles.get(0).getTitle();

        // Act
        var actualArticles = articleRepository.findByTagsContainingAndTitleContainingIgnoreCase(tag, query).collectList().block();

        // Assert
        assertNotNull(actualArticles, "Retrieved articles should not be null");
        assertTrue(actualArticles.size() > 0, "Retrieved articles should not be empty");
        actualArticles.forEach(article -> {
            assertEquals(tag, article.getTags(), "Article tag should match");
            assertTrue(article.getTitle().contains(query), "Article title should contain query");
        });
    }


    @Test
    public void whenEditingArticleWithValidId_ReturnEditedArticle(){
        // Arrange
        var savedArticle = articleRepository.findAll().blockFirst();
        assertNotNull(savedArticle);

        var articleId = savedArticle.getArticleIdentifier().getArticleId();

        // Act
        var actualArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(actualArticle);

        var newTitle = "New Title";
        var newBody = "New Body";
        var newTags = "New Tag";
        var newPhotoUrl = "https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg";

        actualArticle.setTitle(newTitle);
        actualArticle.setBody(newBody);
        actualArticle.setTags(newTags);
        actualArticle.setPhotoUrl(newPhotoUrl);

        articleRepository.save(actualArticle).block();

        // Assert
        var editedArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(editedArticle);
        assertEquals(newTitle, editedArticle.getTitle());
        assertEquals(newBody, editedArticle.getBody());
        assertEquals(newTags, editedArticle.getTags());
        assertEquals(newPhotoUrl, editedArticle.getPhotoUrl());
    }

    @Test
    public void whenEditingArticleWithInvalidId_ReturnNotFound() {
        // Arrange
        var articleId = "invalid-id";

        // Act
        var actualArticleMono = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId);

        // Assert
        StepVerifier.create(actualArticleMono)
                .expectNextCount(0)
                .verifyComplete();
    }


    @Test
    public void whenEditingArticleWithValidId_ReturnEditedArticle(){
        // Arrange
        var savedArticle = articleRepository.findAll().blockFirst();
        assertNotNull(savedArticle);

        var articleId = savedArticle.getArticleIdentifier().getArticleId();

        // Act
        var actualArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(actualArticle);

        var newTitle = "New Title";
        var newBody = "New Body";
        var newTags = "New Tag";
        var newPhotoUrl = "https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg";

        actualArticle.setTitle(newTitle);
        actualArticle.setBody(newBody);
        actualArticle.setTags(newTags);
        actualArticle.setPhotoUrl(newPhotoUrl);

        articleRepository.save(actualArticle).block();

        // Assert
        var editedArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(editedArticle);
        assertEquals(newTitle, editedArticle.getTitle());
        assertEquals(newBody, editedArticle.getBody());
        assertEquals(newTags, editedArticle.getTags());
        assertEquals(newPhotoUrl, editedArticle.getPhotoUrl());
    }

    @Test
    public void whenEditingArticleWithInvalidId_ReturnNotFound() {
        // Arrange
        var articleId = "invalid-id";

        // Act
        var actualArticleMono = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId);

        // Assert
        StepVerifier.create(actualArticleMono)
                .expectNextCount(0)
                .verifyComplete();
    }


    @Test
    public void whenEditingArticleWithValidId_ReturnEditedArticle(){
        // Arrange
        var savedArticle = articleRepository.findAll().blockFirst();
        assertNotNull(savedArticle);

        var articleId = savedArticle.getArticleIdentifier().getArticleId();

        // Act
        var actualArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(actualArticle);

        var newTitle = "New Title";
        var newBody = "New Body";
        var newTags = "New Tag";
        var newPhotoUrl = "https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg";

        actualArticle.setTitle(newTitle);
        actualArticle.setBody(newBody);
        actualArticle.setTags(newTags);
        actualArticle.setPhotoUrl(newPhotoUrl);

        articleRepository.save(actualArticle).block();

        // Assert
        var editedArticle = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId).block();
        assertNotNull(editedArticle);
        assertEquals(newTitle, editedArticle.getTitle());
        assertEquals(newBody, editedArticle.getBody());
        assertEquals(newTags, editedArticle.getTags());
        assertEquals(newPhotoUrl, editedArticle.getPhotoUrl());
    }

    @Test
    public void whenEditingArticleWithInvalidId_ReturnNotFound() {
        // Arrange
        var articleId = "invalid-id";

        // Act
        var actualArticleMono = articleRepository.findArticleByArticleIdentifier_ArticleId(articleId);

        // Assert
        StepVerifier.create(actualArticleMono)
                .expectNextCount(0)
                .verifyComplete();
    }
}
