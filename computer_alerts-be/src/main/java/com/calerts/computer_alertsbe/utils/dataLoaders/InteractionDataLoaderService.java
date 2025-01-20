//package com.calerts.computer_alertsbe.utils.dataLoaders;
//
//import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.*;
//import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//
//@Service
//public class InteractionDataLoaderService implements CommandLineRunner {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private SaveRepository saveRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Comment comment1 = Comment.builder()
//                .commentId(new CommentIdentifier("0a3cae94-d32c-4d3d-94cd-6c9532b54a09"))
//                .content("Great article! I learned a lot.")
//                .wordCount(6)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment2 = Comment.builder()
//                .commentId(new CommentIdentifier("ef759e21-066d-4255-973f-b43fe2b6e489"))
//                .content("Great analysis of the game! Really insightful.")
//                .wordCount(7)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment3 = Comment.builder()
//                .commentId(new CommentIdentifier("6fb93682-e1c7-4952-b344-30235b014363"))
//                .content("Why do you think he had a slow start to the season?")
//                .wordCount(12)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment4 = Comment.builder()
//                .commentId(new CommentIdentifier("33994e95-792d-43a1-9571-660d83567f73"))
//                .content("I think they should try a different defensive strategy.")
//                .wordCount(9)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment5 = Comment.builder()
//                .commentId(new CommentIdentifier("8b3c066c-f5a8-4966-9cd3-f95a5e1194d8"))
//                .content("Did you know he used to play for Team Barca?")
//                .wordCount(10)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment6 = Comment.builder()
//                .commentId(new CommentIdentifier("a3d4d286-e99b-40ce-9cde-cfe2804293aa"))
//                .content("That was a hilarious blunder by him!")
//                .wordCount(7)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment7 = Comment.builder()
//                .commentId(new CommentIdentifier("2be38363-97bd-4cda-b92a-34e46935d4a3"))
//                .content("Good article. Can't wait for the next one!")
//                .wordCount(8)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment8 = Comment.builder()
//                .commentId(new CommentIdentifier("46d0610e-5a0c-4398-8bc4-0af9ec3df3e4"))
//                .content("I disagree with the author's take on their performance.")
//                .wordCount(9)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment9 = Comment.builder()
//                .commentId(new CommentIdentifier("68fdd25c-1771-4b4a-a9ce-363052fc9112"))
//                .content("Can you provide more details about the injury?")
//                .wordCount(8)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment10 = Comment.builder()
//                .commentId(new CommentIdentifier("c7802280-e952-4ea9-8c75-b11389e10a9b"))
//                .content("I was at the game last night and it was electric!")
//                .wordCount(11)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Comment comment11 = Comment.builder()
//                .commentId(new CommentIdentifier("9d46d4bb-8d5d-4180-b6a1-ad74483c9ede"))
//                .content("tis article is dogshit")
//                .wordCount(4)
//                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save1 = Save.builder()
//                .saveId(new SaveIdentifier("fd590cec-7f98-455b-a4db-0f9a6b007c8a"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save2 = Save.builder()
//                .saveId(new SaveIdentifier("3b9b288b-bdaa-4182-853f-6b7a38ee448d"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("806d087b-e708-4293-925b-40b2972741e5"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save3 = Save.builder()
//                .saveId(new SaveIdentifier("aebb0bd2-2950-4453-8f5b-d596d0d27266"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("1f9e4567-8901-4b5c-a23d-4567890123ef"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save4 = Save.builder()
//                .saveId(new SaveIdentifier("f5d3f295-82bc-4df9-b6da-3b9130ecb0de"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("ca1d0478-6a9c-421b-b815-84965e3c7b4a"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save5 = Save.builder()
//                .saveId(new SaveIdentifier("d16c5c9a-82c2-4c89-9e9e-b8e4620a12ef"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("2a3b4c5d-6e7f-8g9h-0i1j-2k3l4m5n6o7p"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save6 = Save.builder()
//                .saveId(new SaveIdentifier("15cba998-5c75-4787-81d1-c987fc38cbd8"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("3d4e5f6g-7h8i-9j0k-1l2m-3n4o5p6q7r8s"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save7 = Save.builder()
//                .saveId(new SaveIdentifier("9247e38b-f62f-4191-9d7a-f1ed4a77b6ab"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("4e5f6g7h-8i9j-0k1l-2m3n-4o5p6q7r8s9t"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save8 = Save.builder()
//                .saveId(new SaveIdentifier("3986e28f-87a6-45e4-b68c-d66c0d926f00"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("5f6g7h8i-9j0k-1l2m-3n4o-5p6q7r8s9t0u"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save9 = Save.builder()
//                .saveId(new SaveIdentifier("5752901c-e243-4577-b105-fc1bb0904563"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("6g7h8i9j-0k1l-2m3n-4o5p-6q7r8s9t0u1v"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
//        Save save10 = Save.builder()
//                .saveId(new SaveIdentifier("fa76b0e6-bf76-4c6b-a5de-37bff7d870ab"))
////                .timestamp(LocalDateTime.now())
//                .articleId(new ArticleIdentifier("7h8i9j0k-1l2m-3n4o-5p6q-7r8s9t0u1v2w"))
//                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
//                .build();
//
////        Save save11 = Save.builder()
////                .saveId(new SaveIdentifier("0ffcd990-1bc3-41a7-8462-cc5f52466c5d"))
////                .timestamp(LocalDateTime.now())
////                .articleId(new ArticleIdentifier("1f9e4567-8901-4b5c-a23d-4567890123ef"))
////                .readerId("06a7d573-bcab-4db3-956f-773324b92a80")
////                .build();
//
//        Flux.just(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8, comment9, comment10, comment11)
//                .flatMap(s -> commentRepository.insert(Mono.just(s))
//                        .log(s.toString()))
//                .subscribe();
//
//         Flux.just(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8, comment9, comment10, comment11)
//                 .flatMap(s -> commentRepository.insert(Mono.just(s))
//                         .log(s.toString()))
//                 .subscribe();
//
//         Flux.just(save1, save2, save3, save4, save5, save6, save7, save8, save9, save10)
//                 .flatMap(s -> saveRepository.insert(Mono.just(s))
//                         .log(s.toString()))
//                 .subscribe();
//    }
//}
