package com.calerts.computer_alertsbe.readerservice.businesslayer;


import com.calerts.computer_alertsbe.readerservice.presentationlayer.ReaderResponseModel;
import reactor.core.publisher.Flux;

public interface ReaderService {

    Flux<ReaderResponseModel> getReaders();


}
