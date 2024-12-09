package com.calerts.computer_alertsbe.articleservice.presentationlayer;



import com.calerts.computer_alertsbe.articleservice.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Get all articles for specific sport
    @GetMapping("/tag/{tagName}")
    public Flux<ArticleResponseModel>getAllArticleForASpecificSport(@PathVariable String tagName) {
        return articleService.getAllArticleForSpecificSport(tagName);


    }
    //Get article by article id
    @GetMapping(value = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ArticleResponseModel>> getArticleByArticleId(@PathVariable String articleId) {
        return Mono.just(articleId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
                .flatMap(articleService::getArticleByArticleId)
                .map(ResponseEntity::ok);
    }
    @PatchMapping(value = "/{articleId}")
    public Mono<ResponseEntity<Void>> incrementRequestCount(@PathVariable String articleId) {
        return articleService.requestCount(articleId).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
