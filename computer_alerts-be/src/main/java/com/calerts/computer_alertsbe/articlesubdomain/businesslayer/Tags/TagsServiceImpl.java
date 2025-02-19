package com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Tags;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags.TagsRepository;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags.TagsRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags.TagsResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Flux<TagsResponseModel> getAllTags() {
        return tagsRepository.findAll().map(EntityModelUtil::toTagsResponseModel);
    }

    @Override
    public Mono<TagsResponseModel> createNewTag(Mono<TagsRequestModel> tagsRequestModel) {
        return tagsRequestModel
                .filter(tags -> tags.getTagName() != null && !tags.getTagName().isEmpty())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Category name must not be empty")))
                .map(EntityModelUtil::toTagsEntity)
                .flatMap(tagsRepository::save)
                .map(EntityModelUtil::toTagsResponseModel);
    }


    @Override
    public Flux<TagsResponseModel> searchTagsByName(String tagName) {
        return tagsRepository.findByTagNameContainingIgnoreCase(tagName)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No tags with this name were found " + tagName))))
                .map(EntityModelUtil::toTagsResponseModel);
    }

}
