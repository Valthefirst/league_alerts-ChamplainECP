package com.calerts.computer_alertsbe.readerservice.businesslayer;


import com.calerts.computer_alertsbe.readerservice.dataaccesslayer.ReaderRepository;
import com.calerts.computer_alertsbe.readerservice.presentationlayer.ReaderResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReaderServiceImpl implements ReaderService{

    @Autowired
    private ReaderRepository repo;




    @Override
    public Flux<ReaderResponseModel> getReaders() {
        return repo.findAll().map(EntityModelUtil::toReaderResponseModel);
    }
}
