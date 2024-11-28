package com.calerts.computer_alertsbe.articleservice.presentationlayer;


import com.calerts.computer_alertsbe.articleservice.businesslayer.ArticleService;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //Get article by article id
    @GetMapping("/{articleId}")
    public Mono<ResponseEntity<ArticleResponseModel>> getArticleByArticleId(@PathVariable String articleId) {
        return Mono.just(articleId)
                .filter(id -> id.length() == 36)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided article id is invalid: " + articleId)))
                .flatMap(articleService::getArticleByArticleId)
                .map(ResponseEntity::ok);
    }
}
