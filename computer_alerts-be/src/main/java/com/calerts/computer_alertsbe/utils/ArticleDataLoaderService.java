package com.calerts.computer_alertsbe.utils;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ArticleDataLoaderService implements CommandLineRunner {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void run(String... args) throws Exception {

        Content content1 = Content.builder()
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .build();

        Article article1 = Article.builder()
                .articleId(new ArticleIdentifer())
                .title(content1.getTitle())
                .body(content1.getBody())
                .wordCount(content1.getWordCount())
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        Content content2 = Content.builder()
                .title("Article 2")
                .body("This is the body of article 2")
                .wordCount(7)
                .build();

        Article article2 = Article.builder()
                .articleId(new ArticleIdentifer())
                .title(content2.getTitle())
                .body(content2.getBody())
                .wordCount(content2.getWordCount())
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        // Check if the article already exists and insert it only if it doesn't
        articleRepository.findArticleByArticleId(String.valueOf(article1.getArticleId()))
                .flatMap(existingArticle -> {
                    System.out.println("Article with ID already exists: " + existingArticle.getArticleId());
                    return Mono.empty(); // Skip insertion
                })
                .switchIfEmpty(articleRepository.insert(article1))
                .doOnSuccess(article -> System.out.println("Inserted Article: " + article))
                .subscribe();

        // Check if the article already exists and insert it only if it doesn't
        articleRepository.findArticleByArticleId(String.valueOf(article2.getArticleId()))
                .flatMap(existingArticle -> {
                    System.out.println("Article with ID already exists: " + existingArticle.getArticleId());
                    return Mono.empty(); // Skip insertion
                })
                .switchIfEmpty(articleRepository.insert(article2))
                .doOnSuccess(article -> System.out.println("Inserted Article: " + article))
                .subscribe();
    }
}
