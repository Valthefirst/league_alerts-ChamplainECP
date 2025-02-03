package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories;

import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Categories.CategoriesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CategoriesResponseModel> getAllCategories() {
        return categoriesService.getAllSportCategories();
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CategoriesResponseModel> createNewCategory(@RequestBody Mono<CategoriesRequestModel> categoriesRequestModel) {
        return categoriesService.createNewCategory(categoriesRequestModel);
    }
}
