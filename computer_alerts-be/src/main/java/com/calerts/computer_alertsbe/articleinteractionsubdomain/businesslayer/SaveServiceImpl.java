package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.SaveIdentifier;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.SaveRepository;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.SaveRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.SaveResponseModel;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.DuplicateSaveException;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SaveServiceImpl implements SaveService{

    private final SaveRepository saveRepository;
    private final ArticleRepository articleRepository;

    public SaveServiceImpl(SaveRepository saveRepository, ArticleRepository articleRepository) {
        this.saveRepository = saveRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Flux<SaveResponseModel> getAllSaves(String readerId) {
        return saveRepository.findSavesByReaderId(readerId)
                .map(EntityModelUtil::toSaveResponseModel);
    }

    @Override
    public Mono<SaveResponseModel> addSave(Mono<SaveRequestModel> saveRequestModel) {
        return saveRequestModel
                .flatMap(requestModel -> saveRepository
                        .findSaveByArticleId_ArticleIdAndReaderId(requestModel.getArticleId(),
                                requestModel.getReaderId()).hasElement().flatMap(
                                        exists -> exists ? Mono.error(new DuplicateSaveException(
                                        "Article is already saved. Article id: " + requestModel.getArticleId())) :
                                                Mono.just(requestModel)
                                )
                )
                .flatMap(requestModel ->
                        articleRepository.findArticleByArticleIdentifier_ArticleId(requestModel.getArticleId())
                                .switchIfEmpty(Mono.error(new NotFoundException("Article id not found: "
                                        + requestModel.getArticleId()))).thenReturn(requestModel)
                )
                .map(EntityModelUtil::toSaveEntity)
                .doOnNext(save -> save.setSaveId(new SaveIdentifier()))
                .flatMap(saveRepository::save)
                .map(EntityModelUtil::toSaveResponseModel);
    }

    @Override
    public Mono<Void> deleteSave(String saveId) {
        return saveRepository.findSaveBySaveId_SaveId(saveId)
                .switchIfEmpty(Mono.error(new NotFoundException("Save id not found: " + saveId)))
                .flatMap(saveRepository::delete)
                .then();
    }
}
