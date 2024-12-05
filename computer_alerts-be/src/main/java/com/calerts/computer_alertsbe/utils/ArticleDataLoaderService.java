package com.calerts.computer_alertsbe.utils;


import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class ArticleDataLoaderService implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public void run(String... args) throws Exception {


        Content content1 = Content.builder()
                .title("Article 1")
                .body("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                        "\n")
                .build();


        Article article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(content1.getTitle())
                .body(content1.getBody())
                .wordCount(Content.calculateWordCount(content1.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        Content content2 = Content.builder()
                .title("Article 2")
                .body("This is the body of article 2")
                .build();

        Article article2 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(content2.getTitle())
                .body(content2.getBody())
                .wordCount(Content.calculateWordCount(content2.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        Content content3 = Content.builder()
                .title("Article 3")
                .body("This is the body of article 3")
                .build();

        Article article3 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(content3.getTitle())
                .body(content3.getBody())
                .wordCount(Content.calculateWordCount(content3.getBody()))
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NFL")
                .timePosted(ZonedDateTime.now().toLocalDateTime())
                .build();

        // Check if the article already exists and insert it only if it doesn't
        Flux.just(article1, article2, article3)
                .flatMap(articleRepository::insert)
                .log()
                .subscribe();

        Flux.just(content1, content2, content3)
                .flatMap(contentRepository::insert)
                .log()
                .subscribe();
    }
}
