package com.calerts.computer_alertsbe.readersubdomain.businesslayer;


import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderRequestModel;
import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReaderService {

    Flux<ReaderResponseModel> getReaders();
    Mono<ReaderResponseModel> getReaderByAuth0UserID(String auth0UserID);

    Mono<ReaderResponseModel> updateReaderDetails(String auth0UserID, Mono<ReaderRequestModel> readerDetails);

    Mono<Void> suspendReaderAccount(String auth0UserID);


}
