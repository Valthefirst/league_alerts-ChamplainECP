package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
                "org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration"
})
@ActiveProfiles("test")
class LikeServiceUnitTest {

    @Autowired
    private LikeService likeService;

    @MockBean
    private LikeRepository likeRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    void whenValidArticleIdAndReaderId_thenSaveLike() {
        // Arrange
        ArticleIdentifier articleIdentifier = new ArticleIdentifier("12345");
        String readerId = "reader-001";

        Article existingArticle = Article.builder()
                .articleIdentifier(articleIdentifier)
                .title("Sample Article")
                .body("This is a sample article.")
                .likeCount(0)
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Like expectedLike = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        // Mock the repository to return the article
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId()))
                .thenReturn(Mono.just(existingArticle)); // Return an existing article

        // Mock the repository to return the saved like
        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
            Like like = invocation.getArgument(0);
            return Mono.just(like); // Return the same Like object
        });

        // Mock the repository to save the article with incremented like count
        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act and Assert using StepVerifier
        StepVerifier.create(likeService.likeArticle(articleIdentifier, readerId))
                .expectNextMatches(actualLike ->
                        actualLike.getArticleIdentifier().equals(expectedLike.getArticleIdentifier()) &&
                                actualLike.getReaderId().equals(expectedLike.getReaderId())
                )
                .verifyComplete();

        // Verify that the article's like count was incremented
        verify(articleRepository).save(argThat(savedArticle ->
                savedArticle.getLikeCount() == 1 &&
                        savedArticle.getArticleIdentifier().equals(articleIdentifier)
        ));

        // Verify that the Like object was saved
        verify(likeRepository).save(argThat(like ->
                like.getArticleIdentifier().equals(articleIdentifier) &&
                        like.getReaderId().equals(readerId)
        ));
    }



    @Test
    void whenValidArticleIdAndReaderId_thenDeleteLike() {
        // Arrange
        // Arrange
        ArticleIdentifier articleIdentifier = new ArticleIdentifier("12345");
        String readerId = "reader-001";

        Like existingLike = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

// Mock repository methods
        when(likeRepository.findByArticleIdentifierAndReaderId(articleIdentifier, readerId))
                .thenReturn(Mono.just(existingLike));

        when(likeRepository.delete(existingLike)).thenReturn(Mono.empty());

    }

    @Test
    void whenValidArticleId_thenReturnAllLikes() {
        // Arrange
        ArticleIdentifier articleIdentifier = new ArticleIdentifier("12345");

        Like like1 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId("reader-001")
                .timestamp(LocalDateTime.now())
                .build();

        Like like2 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId("reader-002")
                .timestamp(LocalDateTime.now())
                .build();

        // Mock the repository to return a Flux<Like>
        when(likeRepository.findByArticleIdentifier(articleIdentifier))
                .thenReturn(Flux.just(like1, like2));

        // Act and Assert using StepVerifier
        StepVerifier.create(likeService.getLikesByArticle(articleIdentifier))
                .expectNext(like1, like2)
                .verifyComplete();
    }

    @Test
    void whenValidReaderId_thenReturnAllLikes() {
        // Arrange
        String readerId = "reader-001";

        Like like1 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier())
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        Like like2 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier())
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        // Mock the repository to return a Flux<Like>
        when(likeRepository.findByReaderId(readerId))
                .thenReturn(Flux.just(like1, like2));

        // Act and Assert using StepVerifier
        StepVerifier.create(likeService.getLikesByReader(readerId))
                .expectNext(like1, like2)
                .verifyComplete();
    }

    @Test
    void whenValidArticleAndReader_thenIncrementLikeCount() {
        // Arrange
        ArticleIdentifier articleIdentifier = new ArticleIdentifier("article-1");
        String readerId = "reader-001";

        Article article = Article.builder()
                .articleIdentifier(articleIdentifier)
                .title("Sample Article")
                .body("This is the body of the article.")
                .likeCount(5) // Initial like count
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Like expectedLike = Like.builder()
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .build(); // Don't set dynamic fields for matching

        // Mock the repository methods
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId()))
                .thenReturn(Mono.just(article));

        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0))); // Save the updated article

        when(likeRepository.save(any(Like.class)))
                .thenAnswer(invocation -> {
                    Like likeToSave = invocation.getArgument(0);
                    likeToSave.setTimestamp(LocalDateTime.now());
                    return Mono.just(likeToSave);
                });

        // Act & Assert using StepVerifier
        StepVerifier.create(likeService.likeArticle(articleIdentifier, readerId))
                .assertNext(like -> {
                    assertEquals(articleIdentifier, like.getArticleIdentifier());
                    assertEquals(readerId, like.getReaderId());
                })
                .verifyComplete();

        // Verify the like count increment
        verify(articleRepository).save(argThat(savedArticle -> savedArticle.getLikeCount() == 6));

        // Verify the Like object saved to the repository
        verify(likeRepository).save(argThat(like ->
                like.getArticleIdentifier().equals(articleIdentifier) &&
                        like.getReaderId().equals(readerId)
        ));
    }

    @Test
    void whenValidArticleAndReader_thenUnlikeArticle() {
        // Arrange
        String articleId = "article-1";
        String readerId = "reader-001";

        // Create an ReaderIdentifier and Article mock object
        ArticleIdentifier articleIdentifier = new ArticleIdentifier(articleId);
        Article article = Article.builder()
                .articleIdentifier(articleIdentifier)
                .title("Sample Article")
                .body("This is a test article.")
                .likeCount(5) // Initial like count
                .photoUrl("https://example.com/photo.jpg")
                .build();

        // Create a Like mock object
        Like like = Like.builder()
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .build();

        // Mock repository behaviors
        when(likeRepository.findByArticleIdentifierAndReaderId(articleIdentifier, readerId))
                .thenReturn(Mono.just(like)); // Simulate the Like exists

        when(likeRepository.delete(like))
                .thenReturn(Mono.empty()); // Simulate successful deletion

        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleId))
                .thenReturn(Mono.just(article)); // Simulate fetching the Article

        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0))); // Simulate saving the updated Article

        // Act
        likeService.unlikeArticle(articleIdentifier, readerId).block();

        // Assert
        // Verify that the Like is deleted
        verify(likeRepository, times(1))
                .delete(like);

        // Verify that the Article's like count is decremented and saved
        verify(articleRepository, times(1))
                .save(argThat(updatedArticle -> updatedArticle.getLikeCount() == 4));
    }

    @Test
    void whenLikeDoesNotExist_thenUnlikeArticleDoesNothing() {
        // Arrange
        String articleId = "article-1";
        String readerId = "reader-001";

        ArticleIdentifier articleIdentifier = new ArticleIdentifier(articleId);

        // Mock repository behaviors
        when(likeRepository.findByArticleIdentifierAndReaderId(articleIdentifier, readerId))
                .thenReturn(Mono.empty()); // Simulate no Like exists

        // Act
        likeService.unlikeArticle(articleIdentifier, readerId).block();

        // Assert
        // Verify that delete is not called since the Like does not exist
        verify(likeRepository, never()).delete(any(Like.class));

        // Verify that save is not called on the Article repository
        verify(articleRepository, never()).save(any(Article.class));
    }



}
