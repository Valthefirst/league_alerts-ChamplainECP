package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
                "org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration"
})
@ActiveProfiles("test")
class ShareServiceUnitTest {

    @Autowired
    private ShareService shareService;

    @MockBean
    private ShareRepository shareRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    public void whenArticleIdAndReaderIdAreValid_thenShareArticle() {

        // Arrange
        ArticleIdentifier articleIdentifier = new ArticleIdentifier("12345");
        String readerId = "reader-001";

        Article existingArticle = Article.builder()
                .articleIdentifier(articleIdentifier)
                .title("Sample Article")
                .body("This is a sample article.")
                .likeCount(0)
                .shareCount(0)
                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
                .build();

        Share expectedShare = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        // Mock the repository to return the article
        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId()))
                .thenReturn(Mono.just(existingArticle));

        // Mock the repository to return the saved share
        when(shareRepository.save(any(Share.class)))
                .thenAnswer(invocation -> {
                    Share share = invocation.getArgument(0);
                    return Mono.just(share);
                });

        // Mock saving the article with updated share count
        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> {
                    Article article = invocation.getArgument(0);
                    return Mono.just(article);
                });

        // Act and Assert using StepVerifier
        StepVerifier.create(shareService.shareArticle(articleIdentifier, readerId))
                .expectNextMatches(actualShare ->
                        actualShare.getArticleIdentifier().equals(expectedShare.getArticleIdentifier()) &&
                                actualShare.getReaderId().equals(expectedShare.getReaderId())
                )
                .verifyComplete();

        // Verify that the Share object was saved
        verify(shareRepository).save(argThat(share ->
                share.getArticleIdentifier().equals(articleIdentifier) &&
                        share.getReaderId().equals(readerId)
        ));

        // Verify that the article's share count was incremented
        verify(articleRepository).save(argThat(savedArticle ->
                savedArticle.getShareCount() == 1 &&
                        savedArticle.getArticleIdentifier().equals(articleIdentifier)
        ));
    }

    @Test
    public void whenArticleIdIsValid_thenReturnShares() {
        // Arrange

        ArticleIdentifier articleIdentifier = new ArticleIdentifier("12345");

        Share share1 = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId("reader-001")
                .timestamp(LocalDateTime.now())
                .build();

        Share share3 = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId("reader-003")
                .timestamp(LocalDateTime.now())
                .build();

        // Mock the repository to return a Flux
        when(shareRepository.findByArticleIdentifier(eq(articleIdentifier)))
                .thenReturn(Flux.just(share1, share3));

        // Act and Assert using StepVerifier
        StepVerifier.create(shareService.getSharesByArticle(articleIdentifier))
                .expectNext(share1)
                .expectNext(share3)
                .verifyComplete();

        // Verify the repository interaction
        verify(shareRepository).findByArticleIdentifier(eq(articleIdentifier));
    }





    //@Test
    //    void whenValidArticleId_thenReturnAllLikes() {
    //        // Arrange
    //        ReaderIdentifier articleIdentifier = new ReaderIdentifier("12345");
    //
    //        Like like1 = Like.builder()
    //                .likeIdentifier(new LikeIdentifier())
    //                .articleIdentifier(articleIdentifier)
    //                .readerId("reader-001")
    //                .timestamp(LocalDateTime.now())
    //                .build();
    //
    //        Like like2 = Like.builder()
    //                .likeIdentifier(new LikeIdentifier())
    //                .articleIdentifier(articleIdentifier)
    //                .readerId("reader-002")
    //                .timestamp(LocalDateTime.now())
    //                .build();
    //
    //        // Mock the repository to return a Flux<Like>
    //        when(likeRepository.findByArticleIdentifier(articleIdentifier))
    //                .thenReturn(Flux.just(like1, like2));
    //
    //        // Act and Assert using StepVerifier
    //        StepVerifier.create(likeService.getLikesByArticle(articleIdentifier))
    //                .expectNext(like1, like2)
    //                .verifyComplete();
    //    }

//    @Test
//    void whenValidArticleIdAndReaderId_thenSaveLike() {
//        // Arrange
//        ReaderIdentifier articleIdentifier = new ReaderIdentifier("12345");
//        String readerId = "reader-001";
//
//        Article existingArticle = Article.builder()
//                .articleIdentifier(articleIdentifier)
//                .title("Sample Article")
//                .body("This is a sample article.")
//                .likeCount(0)
//                .photoUrl("https://res.cloudinary.com/ddihej6gw/image/upload/v1733944101/pexels-corleone-brown-2930373-4500123_zcgbae.jpg")
//                .build();
//
//        Like expectedLike = Like.builder()
//                .likeIdentifier(new LikeIdentifier())
//                .articleIdentifier(articleIdentifier)
//                .readerId(readerId)
//                .timestamp(LocalDateTime.now())
//                .build();
//
//        // Mock the repository to return the article
//        when(articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId()))
//                .thenReturn(Mono.just(existingArticle)); // Return an existing article
//
//        // Mock the repository to return the saved like
//        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
//            Like like = invocation.getArgument(0);
//            return Mono.just(like); // Return the same Like object
//        });
//
//        // Mock the repository to save the article with incremented like count
//        when(articleRepository.save(any(Article.class)))
//                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
//
//        // Act and Assert using StepVerifier
//        StepVerifier.create(likeService.likeArticle(articleIdentifier, readerId))
//                .expectNextMatches(actualLike ->
//                        actualLike.getArticleIdentifier().equals(expectedLike.getArticleIdentifier()) &&
//                                actualLike.getReaderId().equals(expectedLike.getReaderId())
//                )
//                .verifyComplete();
//
//        // Verify that the article's like count was incremented
//        verify(articleRepository).save(argThat(savedArticle ->
//                savedArticle.getLikeCount() == 1 &&
//                        savedArticle.getArticleIdentifier().equals(articleIdentifier)
//        ));
//
//        // Verify that the Like object was saved
//        verify(likeRepository).save(argThat(like ->
//                like.getArticleIdentifier().equals(articleIdentifier) &&
//                        like.getReaderId().equals(readerId)
//        ));
//    }


}