package com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Categories;

import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories.CategoriesRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories.CategoriesResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriesService {

    Flux<CategoriesResponseModel> getAllSportCategories();

    Mono<CategoriesResponseModel> createNewCategory(Mono<CategoriesRequestModel> categoriesResponseModel);

    Mono<Void> deleteCategory(String categoryName);


}
