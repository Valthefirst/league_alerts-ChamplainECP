package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;



import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    @GetMapping("/tag/{tagName}")
    public Flux<ArticleResponseModel>getAllArticleForASpecificSport(@PathVariable String tagName) {
        return articleService.getAllArticleForSpecificSport(tagName);


    }

    @GetMapping(value = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> getArticleByArticleId(@PathVariable String articleId) {
        return Mono.just(articleId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
                .flatMap(articleService::getArticleByArticleId)
                .map(ResponseEntity::ok);
    }

//    @GetMapping(value = "/admin/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Mono<ResponseEntity<ArticleResponseModel>> getArticleByArticleIdForAdmin(@PathVariable String articleId) {
//        return Mono.just(articleId)
//                .filter(id -> id.length() == 36)
//                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
//                .flatMap(articleService::getArticleByArticleId)
//                .map(ResponseEntity::ok);
//    }


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

    @PermitAll
    @PatchMapping(value = "acceptArticle/{articleId}")
    public Mono<ResponseEntity<Void>> acceptArticle(@PathVariable String articleId) {
        return articleService.acceptArticle(articleId).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
