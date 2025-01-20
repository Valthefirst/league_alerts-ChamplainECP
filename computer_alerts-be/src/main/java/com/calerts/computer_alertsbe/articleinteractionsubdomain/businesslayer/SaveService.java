package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.SaveRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.SaveResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaveService {

    Flux<SaveResponseModel> getAllSaves(String readerId);
    Mono<SaveResponseModel> addSave(Mono<SaveRequestModel> saveRequestModel);
    Mono<Void> deleteSave(String saveId);
}
