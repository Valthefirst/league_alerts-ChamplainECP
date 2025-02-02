package com.calerts.computer_alertsbe.readersubdomain.businesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderIdentifier;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderRepository;
import com.calerts.computer_alertsbe.utils.exceptions.BadRequestException;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
                "org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration"
})
@ActiveProfiles("test")
class ReaderServiceUnitTest {

    @Autowired
    private ReaderService readerService;

    @MockBean
    private ReaderRepository readerRepository;

    @Test
    void validReaderAuth0UserId_thenReturnReader() {
        // Arrange
        ReaderIdentifier validArticleId = new ReaderIdentifier();

        Reader expectedReader = Reader.builder()
                .auth0userId("22222")
                .emailAddress("heel")
                .firstName("james")
                .lastName("hello")
                .readerIdentifier(validArticleId)
                .build();

        // Mock the repository to return a Mono<Article>
        when(readerRepository.findReaderByAuth0userId(expectedReader.getAuth0userId()))
                .thenReturn(Mono.just(expectedReader));

        // Act and Assert using StepVerifier
        StepVerifier.create(readerService.getReaderByAuth0UserID(expectedReader.getAuth0userId()))
                .expectNextMatches(actualReader ->
                        expectedReader.getEmailAddress().equals(actualReader.getEmailAddress())
                )
                .verifyComplete();
    }









}
