package com.calerts.computer_alertsbe.articleservice.businesslayer;

import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articleservice.presentationlayer.ArticleResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

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
                .tags("NBA")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
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
                .tags("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        // Mock the repository to return a Flux<Article>
        when(articleRepository.findAllArticleByTags(expectedArticle.getTags()))
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
        ArticleIdentifier validArticleId = new ArticleIdentifier();
        ArticleIdentifier validArticleId2 = new ArticleIdentifier();
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
                .tags("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();
        Article expectedArticle2 = Article.builder()
                .articleIdentifier(validArticleId2)
                .title(content.getTitle())
                .body(content.getBody())
                .wordCount(Content.calculateWordCount(content.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();


        // Mocking the behavior of the repository
        when(articleRepository.findAll()).thenReturn(Flux.just(expectedArticle), expectedArticle2));
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> {
            Article p = invocation.getArgument(0);
            return Mono.just(p);
        });

        // Act: Call the method to reset request counts
        Mono<Void> result = articleService.resetRequestCounts();

        // Assert: Verify that the Mono completes successfully
        StepVerifier.create(result)
                .verifyComplete();

        // Verify that save was called for each article
        verify(articleRepository, times(2)).save(any(article.class));

        // Verify that the requestCount is now reset to 0 for both articles
        verify(articleRepository).save(argThat(article ->
                article.findArticleByArticleIdentifier_ArticleId().equals(validArticleId) &&
                        article.getRequestCount() == 0
        ));
        verify(articleRepository).save(argThat(article ->
                article.findArticleByArticleIdentifier_ArticleId().equals(validArticleId2) &&
                        article.getRequestCount() == 0
        ));
    }

    @Test
    void testRequestCount_ProductFound() {
        // Arrange
        String productId = "testProductId";
        Product product = Product.builder()
                .productId(productId)
                .requestCount(5)
                .build();

        when(productRepository.findProductByProductId(productId)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Void> result = productService.requestCount(productId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository).findProductByProductId(productId);
        verify(productRepository).save(argThat(savedProduct ->
                savedProduct.getProductId().equals(productId) &&
                        savedProduct.getRequestCount() == 6
        ));
    }

    @Test
    void testRequestCount_ArticleNotFound() {
        // Arrange
        String productId = "nonExistentProductId";

        when(productRepository.findProductByProductId(productId)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = productService.requestCount(productId);

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof NotFoundException;
                    assert error.getMessage().equals("Product id was not found: " + productId);
                })
                .verify();

        verify(productRepository).findProductByProductId(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

}
