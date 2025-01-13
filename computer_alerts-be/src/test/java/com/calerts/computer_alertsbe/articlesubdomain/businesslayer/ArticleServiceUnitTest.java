package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.utils.exceptions.BadRequestException;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
                "org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration"
})
@ActiveProfiles("test")
class ArticleServiceUnitTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    void whenValidArticleId_thenReturnArticle() {
        // Arrange
        ArticleIdentifier validArticleId = new ArticleIdentifier();
        Content content = Content.builder()
                .title("Valid Title")
                .body("This is a valid body of the article.")
                .build();

        Article expectedArticle = Article.builder()
                .articleIdentifier(validArticleId)
                .title(content.getTitle())
                .body(content.getBody())
                .wordCount(Content.calculateWordCount(content.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .category("NBA")
                .likeCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        // Mock the repository to return a Mono<Article>
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(validArticleId.toString()))
                .thenReturn(Mono.just(expectedArticle));

        // Act and Assert using StepVerifier
        StepVerifier.create(articleService.getArticleByArticleId(validArticleId.toString()))
                .expectNextMatches(actualArticle ->
                        actualArticle.getTitle().equals(expectedArticle.getTitle()) &&
                                actualArticle.getBody().equals(expectedArticle.getBody()) &&
                                actualArticle.getWordCount() == expectedArticle.getWordCount() &&
                                actualArticle.getArticleStatus() == expectedArticle.getArticleStatus()
                )
                .verifyComplete();
    }



    @Test
    void whenValidArticleTag_thenReturnAllArticle() {
        // Arrange
        ArticleIdentifier validArticleId = new ArticleIdentifier();
        Content content = Content.builder()
                .title("Valid Title")
                .body("This is a valid body of the article.")
                .build();

        Article expectedArticle = Article.builder()
                .articleIdentifier(validArticleId)
                .title(content.getTitle())
                .body(content.getBody())
                .wordCount(Content.calculateWordCount(content.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .category("NFL")
                .likeCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .photoUrl("\"https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg\"")
                .build();

        // Mock the repository to return a Flux<Article>
        when(articleRepository.findAllArticleByCategory(expectedArticle.getCategory()))
                .thenReturn(Flux.just(expectedArticle)); // Return Flux<Article>

        // Act and Assert using StepVerifier
        StepVerifier.create(articleService.getAllArticleForSpecificSport("NFL"))
                .expectNextMatches(actualArticle ->
                        // Assuming EntityModelUtil::toArticleResponseModel is used to map the Article to ArticleResponseModel
                        actualArticle.getTitle().equals(expectedArticle.getTitle()) &&
                                actualArticle.getBody().equals(expectedArticle.getBody()) &&
                                actualArticle.getWordCount() == expectedArticle.getWordCount() &&
                                actualArticle.getArticleStatus() == expectedArticle.getArticleStatus()
                )
                .verifyComplete();
    }
    @Test
    void testResetRequestCounts() {
        // Arrange
        ArticleIdentifier validArticleId = new ArticleIdentifier();
        ArticleIdentifier validArticleId2 = new ArticleIdentifier();
        Content content = Content.builder()
                .title("Valid Title")
                .body("This is a valid body of the article.")
                .build();

        // Create two articles with non-zero request counts
        Article expectedArticle1 = Article.builder()
                .articleIdentifier(validArticleId)
                .title(content.getTitle())
                .body(content.getBody())
                .wordCount(Content.calculateWordCount(content.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .category("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .requestCount(5) // Non-zero request count
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Article expectedArticle2 = Article.builder()
                .articleIdentifier(validArticleId2)
                .title(content.getTitle())
                .body(content.getBody())
                .wordCount(Content.calculateWordCount(content.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .category("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .requestCount(3)
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944094/pexels-bylukemiller-13978862_sm4ynn.jpg")// Non-zero request count
                .build();

        // Mock the repository to return both articles
        when(articleRepository.findAll()).thenReturn(Flux.just(expectedArticle1, expectedArticle2));

        // Mock the save method to return the article being saved
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> {
            Article article = invocation.getArgument(0);
            return Mono.just(article);
        });

        // Act: Call the method to reset request counts
        Mono<Void> result = articleService.resetRequestCounts();

        // Assert: Verify that the Mono completes successfully
        StepVerifier.create(result)
                .verifyComplete();

        // Verify that save was called for each article with requestCount reset to 0
        verify(articleRepository, times(2)).save(argThat(article ->
                article.getRequestCount() == 0
        ));
    }

    @Test
    void testRequestCount_ArticleFound() {
        // Arrange
        String articleId = "testArticleId";
        Article article = Article.builder()
                .articleIdentifier(new ArticleIdentifier(articleId)) // Assuming ArticleIdentifier is part of Article
                .requestCount(5)
                .build();

        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleId))
                .thenReturn(Mono.just(article));
        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Void> result = articleService.requestCount(articleId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(articleRepository).findArticleByArticleIdentifier_ArticleId(articleId);
        verify(articleRepository).save(argThat(savedArticle ->
                savedArticle.getArticleIdentifier().getArticleId().equals(articleId) &&
                        savedArticle.getRequestCount() == 6 // Ensures count is incremented
        ));
    }

    @Test
    void testRequestCount_WhenArticleHasNoRequestCount() {
        // Arrange
        String articleId = "testArticleId";
        Article article = Article.builder()
                .articleIdentifier(new ArticleIdentifier(articleId)) // Assuming ArticleIdentifier is part of Article
                .requestCount(null) // No initial request count
                .build();

        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleId))
                .thenReturn(Mono.just(article));
        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Void> result = articleService.requestCount(articleId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(articleRepository).findArticleByArticleIdentifier_ArticleId(articleId);
        verify(articleRepository).save(argThat(savedArticle ->
                savedArticle.getArticleIdentifier().getArticleId().equals(articleId) &&
                        savedArticle.getRequestCount() == 1 // Ensures count is initialized to 0 and incremented
        ));
    }

    @Test
    void testRequestCount_WhenArticleNotFound() {
        // Arrange
        String articleId = "nonExistentArticleId";

        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleId))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = articleService.requestCount(articleId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().contains("article id was not found: " + articleId)
                )
                .verify();

        verify(articleRepository).findArticleByArticleIdentifier_ArticleId(articleId);
        verify(articleRepository, never()).save(any());
    }

    @Test
    void createArticle_validArticle_shouldCreateAndReturnArticle() {
        // Arrange
        ArticleRequestModel validArticleRequest = ArticleRequestModel.builder()
                .title("Test Article")
                .body("This is a valid test article with sufficient word count to pass the validation.")
                .wordCount(120)
                .category("NBA")
                .build();

        Article savedArticle = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(validArticleRequest.getTitle())
                .body(validArticleRequest.getBody())
                .wordCount(validArticleRequest.getWordCount())
                .articleStatus(ArticleStatus.ARTICLE_REVIEW)
                .category(validArticleRequest.getCategory())
                .requestCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        // Mock the repository save method to return the saved article
        when(articleRepository.save(any(Article.class)))
                .thenReturn(Mono.just(savedArticle));

        // Act and Assert
        StepVerifier.create(articleService.createArticle(Mono.just(validArticleRequest)))
                .expectNextMatches(responseModel ->
                        responseModel.getTitle().equals(validArticleRequest.getTitle()) &&
                                responseModel.getWordCount() == validArticleRequest.getWordCount() &&
                                responseModel.getArticleStatus() == ArticleStatus.ARTICLE_REVIEW
                )
                .verifyComplete();
    }

    @Test
    void createArticleDraft_validArticle_shouldCreateAndReturnArticle() {
        // Arrange
        ArticleRequestModel validArticleRequest = ArticleRequestModel.builder()
                .title("Test Article")
                .body("This is a valid test article with sufficient word count to pass the validation.")
                .wordCount(120)
                .category("NBA")
                .build();

        Article savedArticle = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(validArticleRequest.getTitle())
                .body(validArticleRequest.getBody())
                .wordCount(validArticleRequest.getWordCount())
                .articleStatus(ArticleStatus.DRAFT)
                .category(validArticleRequest.getCategory())
                .requestCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        // Mock the repository save method to return the saved article
        when(articleRepository.save(any(Article.class)))
                .thenReturn(Mono.just(savedArticle));

        // Act and Assert
        StepVerifier.create(articleService.createArticleDraft(Mono.just(validArticleRequest)))
                .expectNextMatches(responseModel ->
                        responseModel.getTitle().equals(validArticleRequest.getTitle()) &&
                                responseModel.getWordCount() == validArticleRequest.getWordCount() &&
                                responseModel.getArticleStatus() == ArticleStatus.DRAFT
                )
                .verifyComplete();
    }


    @Test
    void createArticle_emptyTitle_shouldThrowBadRequestException() {
        // Arrange
        ArticleRequestModel invalidArticleRequest = ArticleRequestModel.builder()
                .title("")
                .body("Some body")
                .wordCount(120)
                .build();

        // Act and Assert
        StepVerifier.create(articleService.createArticle(Mono.just(invalidArticleRequest)))
                .expectError(BadRequestException.class)
                .verify();
    }
    @Test
    void createArticleDraft_emptyTitle_shouldThrowBadRequestException() {
        // Arrange
        ArticleRequestModel invalidArticleRequest = ArticleRequestModel.builder()
                .title("")
                .body("Some body")
                .wordCount(120)
                .build();

        // Act and Assert
        StepVerifier.create(articleService.createArticleDraft(Mono.just(invalidArticleRequest)))
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void createArticle_insufficientWordCount_shouldThrowBadRequestException() {
        // Arrange
        ArticleRequestModel invalidArticleRequest = ArticleRequestModel.builder()
                .title("Test Title")
                .body("Short body")
                .wordCount(50)
                .build();

        // Act and Assert
        StepVerifier.create(articleService.createArticle(Mono.just(invalidArticleRequest)))
                .expectError(BadRequestException.class)
                .verify();
    }
    @Test
    void createArticleDraft_insufficientWordCount_shouldThrowBadRequestException() {
        // Arrange
        ArticleRequestModel invalidArticleRequest = ArticleRequestModel.builder()
                .title("Test Title")
                .body("Short body")
                .wordCount(50)
                .build();

        // Act and Assert
        StepVerifier.create(articleService.createArticleDraft(Mono.just(invalidArticleRequest)))
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void acceptArticle_validArticleId_shouldUpdateArticleStatus() {
        // Arrange
        String validArticleId = "validArticleId";
        Article existingArticle = Article.builder()
                .articleIdentifier(new ArticleIdentifier(validArticleId))
                .title("Test Article")
                .body("Article body")
                .articleStatus(ArticleStatus.ARTICLE_REVIEW)
                .build();

        // Mock the repository find method to return the existing article
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(validArticleId))
                .thenReturn(Mono.just(existingArticle));

        // Mock the repository save method to return the updated article
        when(articleRepository.save(any(Article.class)))
                .thenReturn(Mono.just(existingArticle));

        // Act and Assert
        StepVerifier.create(articleService.acceptArticle(validArticleId))
                .verifyComplete();
    }

    @Test
    void acceptArticle_nonExistentArticleId_shouldThrowNotFoundException() {
        // Arrange
        String nonExistentArticleId = "nonExistentArticleId";

        // Mock the repository find method to return empty
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(nonExistentArticleId))
                .thenReturn(Mono.empty());

        // Act and Assert
        StepVerifier.create(articleService.acceptArticle(nonExistentArticleId))
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().contains("article id was not found: " + nonExistentArticleId)
                )
                .verify();
    }

    @Test
    void searchArticles_ShouldReturnMatchingArticles() {
        // Arrange
        String tag = "NBA";
        String query = "Test";

        Article article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Test Article 1")
                .body("This is a test article 1")
                .wordCount(5)
                .articleStatus(ArticleStatus.PUBLISHED)
                .category(tag)
                .likeCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944091/pexels-introspectivedsgn-7783413_r7s5xx.jpg")
                .build();

        Article article2 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title("Test Article 2")
                .body("This is a test article 2")
                .wordCount(5)
                .articleStatus(ArticleStatus.PUBLISHED)
                .category(tag)
                .likeCount(0)
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944091/pexels-introspectivedsgn-7783413_r7s5xx.jpg")
                .build();

        // Mock the repository to return a Flux<Article>
        when(articleRepository.findByCategoryContainingAndTitleContainingIgnoreCase(tag, query))
                .thenReturn(Flux.just(article1, article2));

        // Act and Assert using StepVerifier
        StepVerifier.create(articleService.searchArticles(tag, query))
                .expectNextMatches(actualArticles -> actualArticles.size() == 2)
                .verifyComplete();

        // Verify that the repository method was called with the correct arguments
        verify(articleRepository).findByCategoryContainingAndTitleContainingIgnoreCase(tag, query);



    }

    @Test
    void whenUpdateArticle_validArticle_shouldUpdateAndReturnArticle() {
        // Arrange
        String validArticleId = "validArticleId";
        ArticleRequestModel validArticleRequest = ArticleRequestModel.builder()
                .title("Test Article")
                .body("This is a valid test article with sufficient word count to pass the validation.")
                .category("NBA")
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Article existingArticle = Article.builder()
                .articleIdentifier(new ArticleIdentifier(validArticleId))
                .title("Old Title")
                .body("Old body")
                .articleStatus(ArticleStatus.ARTICLE_REVIEW)
                .category("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Article updatedArticle = Article.builder()
                .articleIdentifier(new ArticleIdentifier(validArticleId))
                .title(validArticleRequest.getTitle())
                .body(validArticleRequest.getBody())
                .wordCount(validArticleRequest.getWordCount())
                .articleStatus(ArticleStatus.ARTICLE_REVIEW)
                .category(validArticleRequest.getCategory())
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        // Mock the repository find method to return the existing article
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(validArticleId))
                .thenReturn(Mono.just(existingArticle));

        // Mock the repository save method to return the updated article
        when(articleRepository.save(any(Article.class)))
                .thenReturn(Mono.just(updatedArticle));

        // Act and Assert
        StepVerifier.create(articleService.editArticle(validArticleId, Mono.just(validArticleRequest)))
                .expectNextMatches(responseModel ->
                        responseModel.getTitle().equals(validArticleRequest.getTitle()) &&
                                responseModel.getWordCount() == validArticleRequest.getWordCount() &&
                                responseModel.getArticleStatus() == ArticleStatus.ARTICLE_REVIEW
                )
                .verifyComplete();
    }

    @Test
    void whenUpdateArticle_nonExistentArticle_shouldThrowNotFoundException() {
        // Arrange
        String nonExistentArticleId = "nonExistentArticleId";
        ArticleRequestModel validArticleRequest = ArticleRequestModel.builder()
                .title("Test Article")
                .body("This is a valid test article with sufficient word count to pass the validation.")
                .category("NBA")
                .build();

        // Mock the repository find method to return empty
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(nonExistentArticleId))
                .thenReturn(Mono.empty());

        // Act and Assert
        StepVerifier.create(articleService.editArticle(nonExistentArticleId, Mono.just(validArticleRequest)))
                .expectErrorMatches(throwable ->
                        throwable instanceof NotFoundException &&
                                throwable.getMessage().contains("No article with this id was found " + nonExistentArticleId)
                )
                .verify();
    }

}
