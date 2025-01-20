package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Comment;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentRepository;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentResponseModel;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidCommentException;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Flux<CommentResponseModel> getAllComments() {
        return commentRepository.findAll()
                .map(EntityModelUtil::toCommentResponseModel);
    }

    @Override
    public Mono<CommentResponseModel> addComment(Mono<CommentRequestModel> commentRequestModel) {
        return commentRequestModel
                .filter(comment -> !comment.getContent().trim().isEmpty())
                .switchIfEmpty(Mono.error(new InvalidCommentException("Comment content cannot be empty")))
                .flatMap(c ->{
                    if (c.getContent().trim().split(" ").length > 50)
                        return Mono.error(new InvalidCommentException("Comment exceeds 50 words."));
                    return Mono.just(c);
                })
                .flatMap(c -> {
                    return articleRepository.findArticleByArticleIdentifier_ArticleId(c.getArticleId())
                            .switchIfEmpty(Mono.error(new NotFoundException("Article id was not found: " + c.getArticleId())))
                            .map(article -> c);
                })
                .flatMap(c -> {
                    Comment comment = EntityModelUtil.toCommentEntity(c);
                    comment.setCommentId(new CommentIdentifier());
                    comment.setTimestamp(LocalDateTime.now());
                    comment.setContent(comment.getContent().trim());
                    comment.setWordCount(comment.getContent().trim().split(" ").length);
                    return Mono.just(comment);
                })
                .flatMap(commentRepository::save)
                .map(EntityModelUtil::toCommentResponseModel);
    }
}
