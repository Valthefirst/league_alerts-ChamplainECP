package com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Categories;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories.CategoriesRepository;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories.CategoriesRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories.CategoriesResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Flux<CategoriesResponseModel> getAllSportCategories() {
        return categoriesRepository.findAll().map(EntityModelUtil::toCategoriesResponseModel);
    }

    @Override
    public Mono<CategoriesResponseModel> createNewCategory(Mono<CategoriesRequestModel> categoriesRequestModelMono) {
        return categoriesRequestModelMono
                .filter(category -> category.getCategoryName() != null && !category.getCategoryName().isEmpty())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Category name must not be empty")))
                .map(EntityModelUtil::toCategoriesEntity)
                .flatMap(categoriesRepository::save)
                .map(EntityModelUtil::toCategoriesResponseModel);
    }

    @Override
    public Mono<Void> deleteCategory(String categoryId) {
        return null;
    }
}
