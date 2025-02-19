package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.LikeRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    public LikeServiceImpl(LikeRepository likeRepository, ArticleRepository articleRepository) {
        this.likeRepository = likeRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Mono<Like> likeArticle(ArticleIdentifier articleIdentifier, String readerId) {
        Like like = Like.builder()
                .likeIdentifier(new LikeIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .build();

        return likeRepository.save(like)
                .flatMap(savedLike -> articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId())
                        .flatMap(article -> {
                            article.incrementLikeCount();
                            return articleRepository.save(article);
                        })
                        .thenReturn(savedLike));
    }

    @Override
    public Flux<Like> getLikesByArticle(ArticleIdentifier articleIdentifier) {
        // Find all Likes associated with a specific ReaderIdentifier
        return likeRepository.findByArticleIdentifier(articleIdentifier);
    }

    @Override
    public Flux<Like> getLikesByReader(String readerId) {
        return likeRepository.findByReaderId(readerId);
    }

    @Override
    public Mono<Like> getLikeByIdentifier(String likeId) {
        return likeRepository.findByLikeIdentifier_LikeId(likeId);
    }

    @Override
    public Mono<Void> unlikeArticle(ArticleIdentifier articleIdentifier, String readerId) {
        return likeRepository.findByArticleIdentifierAndReaderId(articleIdentifier, readerId)
                .flatMap(like -> likeRepository.delete(like) // Delete the like
                        .then(articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId())
                                .flatMap(article -> {
                                    article.decrementLikeCount(); // Decrement the like count
                                    return articleRepository.save(article); // Save the updated article
                                })
                        )
                ).then();
    }

}
