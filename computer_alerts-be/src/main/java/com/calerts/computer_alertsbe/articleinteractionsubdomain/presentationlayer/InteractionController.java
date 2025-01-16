package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer.LikeService;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer.ShareService;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Share;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.springframework.http.HttpStatus;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer.CommentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interactions")
public class InteractionController {

    private final LikeService likeService;
    private final CommentService commentService;

    private final ShareService shareService;

    public InteractionController(LikeService likeService, CommentService commentService, ShareService shareService) {
        this.likeService = likeService;
        this.commentService = commentService;
        this.shareService = shareService;
    }


    @PostMapping("/like")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<LikeResponseModel>> likeArticle(
            @RequestParam String articleId,
            @RequestParam String readerId) {

        ArticleIdentifier articleIdentifier = new ArticleIdentifier(articleId);
        return likeService.likeArticle(articleIdentifier, readerId)
                .map(EntityModelUtil::toLikeResponseModel)
                .map(responseModel -> ResponseEntity.status(HttpStatus.CREATED).body(responseModel));
    }


    @GetMapping("/likes/article/{articleId}")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<List<LikeResponseModel>>> getLikesByArticle(@PathVariable String articleId) {
        ArticleIdentifier identifier = new ArticleIdentifier(articleId);
        return likeService.getLikesByArticle(identifier)
                .map(EntityModelUtil::toLikeResponseModel)
                .collectList() // Collect the Flux into a List
                .map(ResponseEntity::ok);
    }

    @GetMapping("/likes/reader/{readerId}")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<List<LikeResponseModel>>> getLikesByReader(@PathVariable String readerId) {
        return likeService.getLikesByReader(readerId)
                .map(EntityModelUtil::toLikeResponseModel)
                .collectList()
                .map(likes -> {
                    if (likes.isEmpty()) {
                        return ResponseEntity.noContent().build();
                    }
                    return ResponseEntity.ok(likes);
                });
    }

    @GetMapping("/likes/{likeId}")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<LikeResponseModel>> getLikeByIdentifier(@PathVariable String likeId) {
        return likeService.getLikeByIdentifier(likeId)
                .map(EntityModelUtil::toLikeResponseModel)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/unlike")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<Void>> unlikeArticle(
            @RequestParam String articleId,
            @RequestParam String readerId) {

        ArticleIdentifier articleIdentifier = new ArticleIdentifier(articleId);
        return likeService.unlikeArticle(articleIdentifier, readerId)
                .then(Mono.just(ResponseEntity.noContent().build())); // Return 204 No Content on success
    }

    @GetMapping(value = "/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAuthority('like:articles')")
    public Flux<CommentResponseModel> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<Void>> addComment(@RequestBody Mono<CommentRequestModel> commentRequestModel) {
        return commentService.addComment(commentRequestModel)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

//    @PostMapping("/share")
//    public Mono<String> generateShareLink(@RequestParam String articleId, @RequestParam String readerId) {
//        ReaderIdentifier articleIdentifier = new ReaderIdentifier(articleId);
//        return shareService.shareArticle(articleIdentifier, readerId)
//                .map(Share::getShareLink);
//    }

    @PostMapping("/share")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<ShareResponseModel>> shareArticle(
            @RequestParam String articleId,
            @RequestParam String readerId) {

        ArticleIdentifier articleIdentifier = new ArticleIdentifier(articleId);
        return shareService.shareArticle(articleIdentifier, readerId)
                .map(EntityModelUtil::toShareResponseModel)
                .map(responseModel -> ResponseEntity.status(HttpStatus.CREATED).body(responseModel));
    }

    @GetMapping("/shares/article/{articleId}")
    @PreAuthorize("hasAuthority('like:articles')")
    public Mono<ResponseEntity<List<ShareResponseModel>>> getSharesByArticle(@PathVariable String articleId) {
        ArticleIdentifier identifier = new ArticleIdentifier(articleId);
        return shareService.getSharesByArticle(identifier)
                .map(EntityModelUtil::toShareResponseModel)
                .collectList() // Collect the Flux into a List
                .map(ResponseEntity::ok);
    }
}
