package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import static org.junit.jupiter.api.Assertions.*;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
class LikeRepositoryIntegrationTest {

    @Autowired
    private LikeRepository likeRepository;

    @BeforeEach
    void setUp() {
        // Clean up the repository before each test to ensure isolation
        likeRepository.deleteAll().block();

        // Add sample data for testing
        var like1 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-1"))
                .readerId("reader-001")
                .timestamp(LocalDateTime.now())
                .build();

        var like2 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-2"))
                .readerId("reader-002")
                .timestamp(LocalDateTime.now())
                .build();

        var like3 = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-1"))
                .readerId("reader-003")
                .timestamp(LocalDateTime.now())
                .build();

        likeRepository.saveAll(List.of(like1, like2, like3)).blockLast();
    }

    @Test
    public void whenArticleIdentifierIsValid_thenReturnLikes() {
        // Arrange
        var savedLikes = likeRepository.findAll().collectList().block();
        assertNotNull(savedLikes, "Saved likes should not be null");

        var articleIdentifier = savedLikes.get(0).getArticleIdentifier();

        // Act
        var actualLikes = likeRepository.findByArticleIdentifier(articleIdentifier).collectList().block();

        // Assert
        assertNotNull(actualLikes, "Retrieved likes should not be null");
        assertTrue(actualLikes.size() > 0, "Retrieved likes should not be empty");

        // Compare the 'articleId' field instead of the entire object
        actualLikes.forEach(like ->
                assertEquals(articleIdentifier.getArticleId(), like.getArticleIdentifier().getArticleId(), "Article ID should match")
        );
    }


    @Test
    public void whenReaderIdIsValid_thenReturnLikes() {
        // Arrange
        var savedLikes = likeRepository.findAll().collectList().block();
        assertNotNull(savedLikes, "Saved likes should not be null");

        var readerId = savedLikes.get(0).getReaderId();
        System.out.println("Expected Reader ID: " + readerId);

        // Act
        var actualLikes = likeRepository.findByReaderId(readerId).collectList().block();
        System.out.println("Retrieved Likes: " + actualLikes);

        // Assert
        assertNotNull(actualLikes, "Retrieved likes should not be null");
        assertTrue(actualLikes.size() > 0, "Retrieved likes should not be empty");

        actualLikes.forEach(like -> {
            System.out.println("Comparing Reader ID: " + like.getReaderId());
            assertEquals(readerId, like.getReaderId(), "Reader ID should match");
        });
    }


    @Test
    public void whenArticleIdentifierAndReaderIdAreValid_thenReturnLike() {
        // Arrange
        var savedLikes = likeRepository.findAll().collectList().block();
        assertNotNull(savedLikes, "Saved likes should not be null");

        var articleIdentifier = savedLikes.get(0).getArticleIdentifier();
        var readerId = savedLikes.get(0).getReaderId();

        // Act
        var actualLike = likeRepository.findByArticleIdentifierAndReaderId(articleIdentifier, readerId).block();

        // Assert
        assertNotNull(actualLike, "Retrieved like should not be null");
        assertEquals(articleIdentifier.getArticleId(), actualLike.getArticleIdentifier().getArticleId(), "Article ID should match");
        assertEquals(readerId, actualLike.getReaderId(), "Reader ID should match");
    }

}
