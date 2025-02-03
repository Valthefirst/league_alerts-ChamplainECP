package com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Tags;


import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags.TagsRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags.TagsResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagsService {

    Flux<TagsResponseModel> getAllTags();

    Mono<TagsResponseModel> createNewTag(Mono<TagsRequestModel> tagsRequestModel);


}
