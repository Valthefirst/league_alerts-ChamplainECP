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

        //var articleIdentifier = new ArticleIdentifier();

        Content content1 = Content.builder()
                .title("Article 1")
                .body("This is the body of article 1")
                .wordCount(7)
                .build();


        Article article1 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
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
                .articleIdentifier(new ArticleIdentifier())
                .title(content2.getTitle())
                .body(content2.getBody())
                .wordCount(content2.getWordCount())
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NBA")
                .timePosted(LocalDateTime.now())
                .build();

        Content content3 = Content.builder()
                .title("Article 3")
                .body("This is the body of article 3")
                .wordCount(7)
                .build();

        Article article3 = Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .title(content3.getTitle())
                .body(content3.getBody())
                .wordCount(content3.getWordCount())
                .articleStatus(ArticleStatus.PUBLISHED)
                .tags("NFL")
                .timePosted(LocalDateTime.now())
                .build();

        // Check if the article already exists and insert it only if it doesn't
        articleRepository.findArticleByArticleIdentifier(article1.getArticleIdentifier().getArticleId())
                .flatMap(existingArticle -> {
                    System.out.println("Article with ID already exists: " + existingArticle.getArticleIdentifier().getArticleId());
                    return Mono.empty(); // Skip insertion
                })
                .switchIfEmpty(articleRepository.insert(article1))
                .doOnSuccess(article -> System.out.println("Inserted Article: " + article))
                .subscribe();

        // Check if the article already exists and insert it only if it doesn't
        articleRepository.findArticleByArticleIdentifier(article2.getArticleIdentifier().getArticleId())
                .flatMap(existingArticle -> {
                    System.out.println("Article with ID already exists: " + existingArticle.getArticleIdentifier().getArticleId());
                    return Mono.empty(); // Skip insertion
                })
                .switchIfEmpty(articleRepository.insert(article2))
                .doOnSuccess(article -> System.out.println("Inserted Article: " + article))
                .subscribe();

        // Check if the article already exists and insert it only if it doesn't
        articleRepository.findArticleByArticleIdentifier(article1.getArticleIdentifier().getArticleId())
                .flatMap(existingArticle -> {
                    System.out.println("Article with ID already exists: " + existingArticle.getArticleIdentifier().getArticleId());
                    return Mono.empty(); // Skip insertion
                })
                .switchIfEmpty(articleRepository.insert(article3))
                .doOnSuccess(article -> System.out.println("Inserted Article: " + article))
                .subscribe();
    }
}
