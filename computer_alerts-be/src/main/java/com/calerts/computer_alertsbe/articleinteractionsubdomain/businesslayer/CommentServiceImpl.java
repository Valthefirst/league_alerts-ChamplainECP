package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentRepository;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Flux<CommentResponseModel> getAllComments() {
        return commentRepository.findAll()
                .map(EntityModelUtil::toCommentResponseModel);
    }

    @Override
    public Mono<CommentResponseModel> addComment(Mono<CommentRequestModel> commentRequestModel) {
        return commentRequestModel
                .map(EntityModelUtil::toCommentEntity)
                .doOnNext(e -> e.setCommentId(new CommentIdentifier()))
                .doOnNext(e -> e.setTimestamp(LocalDateTime.now()))
                .doOnNext(e -> e.setContent(e.getContent().trim()))
                .doOnNext(e -> e.setWordCount(e.getContent().trim().split(" ").length))
                .flatMap(commentRepository::save)
                .map(EntityModelUtil::toCommentResponseModel);
    }
}
