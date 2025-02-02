package com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class ReaderRepositoryIntegrationTest {

    @Autowired
    private ReaderRepository readerRepository;


    @BeforeEach
    void setUp() {
        // Clean up the repository before each test to ensure isolation
        readerRepository.deleteAll().block();

        // Add sample data for testing
        ReaderIdentifier validReaderId = new ReaderIdentifier();

        Reader reader1 = Reader.builder()
                .auth0userId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .readerIdentifier(validReaderId)
                .build();

        readerRepository.saveAll(List.of(reader1)).blockLast();
    }

    @Test
    public void whenReaderIdIsValid_thenReturnReader() {
        // Arrange
        var savedReader = readerRepository.findAll().blockFirst();
        assertNotNull(savedReader, "Saved reader should not be null");

        var readerId = savedReader.getAuth0userId();

        // Act
        var actualReader = readerRepository.findReaderByAuth0userId(readerId).block();

        // Assert
        assertNotNull(actualReader, "Retrieved reader should not be null");
        assertEquals(readerId, actualReader.getAuth0userId(), "Reader ID should match");
    }








}
