package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags;

import com.calerts.computer_alertsbe.articlesubdomain.businesslayer.Tags.TagsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/tags")
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TagsResponseModel> getAllTags() {
        return tagsService.getAllTags();
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TagsResponseModel> createNewTags(@RequestBody Mono<TagsRequestModel> tagsRequestModelMono) {
        return tagsService.createNewTag(tagsRequestModelMono);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TagsResponseModel> searchTags(@RequestParam String tagName) {
        return tagsService.searchTagsByName(tagName);
    }
}
