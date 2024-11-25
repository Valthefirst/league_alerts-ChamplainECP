package com.calerts.computer_alertsbe.ReadersDomain.businessLayer;


import com.calerts.computer_alertsbe.ReadersDomain.presentationLayer.ReaderResponseModel;
import reactor.core.publisher.Flux;

public interface ReaderService {

    Flux<ReaderResponseModel> getReaders();


}
