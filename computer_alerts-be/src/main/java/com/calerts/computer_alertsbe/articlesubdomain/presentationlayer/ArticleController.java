package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;



import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ArticleResponseModel> getAllArticles() {
        return articleService.getAllArticles();

    }

    //Get all articles for specific sport
    @GetMapping("/categories/{category}")
    public Flux<ArticleResponseModel>getAllArticleForASpecificSport(@PathVariable String category) {
        return articleService.getAllArticleForSpecificSport(category);


    }

    @GetMapping(value = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> getArticleByArticleId(@PathVariable String articleId) {
        return Mono.just(articleId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
                .flatMap(articleService::getArticleByArticleId)
                .map(ResponseEntity::ok);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> createArticle(@RequestBody ArticleRequestModel articleRequestModel) {
        return articleService.createArticle(Mono.just(articleRequestModel))
                .map(articleResponseModel -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(articleResponseModel))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(null)));
    }


    @PermitAll
    @PatchMapping(value = "/{articleId}")
    public Mono<ResponseEntity<Void>> incrementRequestCount(@PathVariable String articleId) {
        return articleService.requestCount(articleId).then(Mono.just(ResponseEntity.noContent().build()));
    }

//    @GetMapping(value = "/tag/{tagName}/search", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Mono<List<ArticleResponseModel>> searchArticles(@RequestParam String query) {
//        return articleService.searchArticles(query);
//    }

    @GetMapping(value = "/categories/{category}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ArticleResponseModel>> searchArticles(
            @PathVariable String category,
            @RequestParam String query
    ) {
        return articleService.searchArticles(category, query);
    }


    @PermitAll
    @PatchMapping(value = "acceptArticle/{articleId}")
    public Mono<ResponseEntity<Void>> acceptArticle(@PathVariable String articleId) {
        return articleService.acceptArticle(articleId).then(Mono.just(ResponseEntity.noContent().build()));
    }



    @PostMapping(value = "/acceptDraft" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> createArticleDraft(@RequestBody ArticleRequestModel articleRequestModel) {
        return articleService.createArticleDraft(Mono.just(articleRequestModel))
                .map(articleResponseModel -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(articleResponseModel))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(null)));
    }

    @PutMapping(value = "/{articleId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> editArticle
            (@PathVariable String articleId,
             @RequestBody Mono<ArticleRequestModel> articleRequestModel) {
        return Mono.just(articleId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
                .flatMap(id -> articleService.editArticle(id, articleRequestModel))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PutMapping(value = "/{articleId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> updateArticleImage(
            @PathVariable String articleId,
            @RequestPart("file") FilePart filePart) {

        System.out.println("cloudinary service" + filePart);
        return articleService.updateArticleImage(articleId, filePart)
                .map(url -> ResponseEntity.ok("Image updated successfully. URL: " + url))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
