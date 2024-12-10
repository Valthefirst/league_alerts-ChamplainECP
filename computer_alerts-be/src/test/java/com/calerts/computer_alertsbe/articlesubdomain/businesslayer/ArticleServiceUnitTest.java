package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.*;
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

}
