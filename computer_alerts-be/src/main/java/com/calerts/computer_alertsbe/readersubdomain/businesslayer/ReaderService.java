package com.calerts.computer_alertsbe.readersubdomain.businesslayer;


import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderResponseModel;
import reactor.core.publisher.Flux;

public interface ReaderService {

    Flux<ReaderResponseModel> getReaders();


}
