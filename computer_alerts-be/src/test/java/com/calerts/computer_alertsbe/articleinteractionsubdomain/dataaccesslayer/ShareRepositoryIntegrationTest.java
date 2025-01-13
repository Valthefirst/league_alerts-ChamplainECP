package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class ShareRepositoryIntegrationTest {

    @Autowired
    private ShareRepository shareRepository;

    @BeforeEach
    void setUp() {
        // Clean up the repository before each test to ensure isolation
        shareRepository.deleteAll().block();

        var share1 = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-1"))
                .readerId("reader-001")
                .timestamp(LocalDateTime.now())
                .build();

        var share2 = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-2"))
                .readerId("reader-002")
                .timestamp(LocalDateTime.now())
                .build();

        var share3 = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(new ArticleIdentifier("article-1"))
                .readerId("reader-003")
                .timestamp(LocalDateTime.now())
                .build();


        shareRepository.saveAll(List.of(share1, share2, share3)).blockLast();

    }


    @Test
    public void whenArticleIdentifierIsValid_thenReturnShares() {
        // Arrange
        var savedShares = shareRepository.findAll().collectList().block();
        assertNotNull(savedShares, "Saved shares should not be null");
        assertTrue(savedShares.size() > 0, "Saved shares should not be empty");

        // Act
        var shares = shareRepository.findByArticleIdentifier(new ArticleIdentifier("article-1")).collectList().block();

        // Assert
        assertNotNull(shares, "Shares should not be null");
        assertTrue(shares.size() > 0, "Shares should not be empty");
        assertEquals(2, shares.size(), "Shares should have size 2");
    }

    @Test
    public void whenArticleIdentifierIsInvalid_thenReturnEmptyList() {
        // Arrange
        var savedShares = shareRepository.findAll().collectList().block();
        assertNotNull(savedShares, "Saved shares should not be null");
        assertTrue(savedShares.size() > 0, "Saved shares should not be empty");

        // Act
        var shares = shareRepository.findByArticleIdentifier(new ArticleIdentifier("article-3")).collectList().block();

        // Assert
        assertNotNull(shares, "Shares should not be null");
        assertTrue(shares.isEmpty(), "Shares should be empty");
    }


}