package com.calerts.computer_alertsbe.utils.dataLoaders;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Comment;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class InteractionDataLoaderService implements CommandLineRunner {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {

        Comment comment1 = Comment.builder()
                .commentId(new CommentIdentifier("0a3cae94-d32c-4d3d-94cd-6c9532b54a09"))
                .content("Great article! I learned a lot.")
                .wordCount(6)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment2 = Comment.builder()
                .commentId(new CommentIdentifier("ef759e21-066d-4255-973f-b43fe2b6e489"))
                .content("Great analysis of the game! Really insightful.")
                .wordCount(7)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment3 = Comment.builder()
                .commentId(new CommentIdentifier("6fb93682-e1c7-4952-b344-30235b014363"))
                .content("Why do you think he had a slow start to the season?")
                .wordCount(12)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment4 = Comment.builder()
                .commentId(new CommentIdentifier("33994e95-792d-43a1-9571-660d83567f73"))
                .content("I think they should try a different defensive strategy.")
                .wordCount(9)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment5 = Comment.builder()
                .commentId(new CommentIdentifier("8b3c066c-f5a8-4966-9cd3-f95a5e1194d8"))
                .content("Did you know he used to play for Team Barca?")
                .wordCount(10)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment6 = Comment.builder()
                .commentId(new CommentIdentifier("a3d4d286-e99b-40ce-9cde-cfe2804293aa"))
                .content("That was a hilarious blunder by him!")
                .wordCount(7)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment7 = Comment.builder()
                .commentId(new CommentIdentifier("2be38363-97bd-4cda-b92a-34e46935d4a3"))
                .content("Good article. Can't wait for the next one!")
                .wordCount(8)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment8 = Comment.builder()
                .commentId(new CommentIdentifier("46d0610e-5a0c-4398-8bc4-0af9ec3df3e4"))
                .content("I disagree with the author's take on their performance.")
                .wordCount(9)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment9 = Comment.builder()
                .commentId(new CommentIdentifier("68fdd25c-1771-4b4a-a9ce-363052fc9112"))
                .content("Can you provide more details about the injury?")
                .wordCount(8)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment10 = Comment.builder()
                .commentId(new CommentIdentifier("c7802280-e952-4ea9-8c75-b11389e10a9b"))
                .content("I was at the game last night and it was electric!")
                .wordCount(11)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Comment comment11 = Comment.builder()
                .commentId(new CommentIdentifier("9d46d4bb-8d5d-4180-b6a1-ad74483c9ede"))
                .content("tis article is dogshit")
                .wordCount(4)
                .timestamp(LocalDateTime.now())
                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
                .build();

        Flux.just(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8, comment9, comment10, comment11)
                .flatMap(s -> commentRepository.insert(Mono.just(s))
                        .log(s.toString()))
                .subscribe();
    }
}
