package com.calerts.computer_alertsbe.readersubdomain.businesslayer;


import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.AccountStatus;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderRepository;
import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderRequestModel;
import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReaderServiceImpl implements ReaderService{

    @Autowired
    private ReaderRepository repo;
    @Autowired
    private ReaderRepository readerRepository;


    @Override
    public Flux<ReaderResponseModel> getReaders() {
        return repo.findAll().map(EntityModelUtil::toReaderResponseModel);
    }

    @Override
    public Mono<ReaderResponseModel> getReaderByAuth0UserID(String auth0UserID) {
        return repo.findReaderByAuth0userId(auth0UserID)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new NotFoundException("No Reader was found with this" + auth0UserID))))
                .map(EntityModelUtil::toReaderResponseModel);
    }

    @Override
    public Mono<ReaderResponseModel> updateReaderDetails(String auth0UserID, Mono<ReaderRequestModel> readerDetails) {
        return readerRepository.findReaderByAuth0userId(auth0UserID)
                .switchIfEmpty(Mono.error(new NotFoundException("No Reader found with auth0UserID: " + auth0UserID)))
                .flatMap(foundReader -> readerDetails.map(readerRequestModel -> {
                    foundReader.setFirstName(readerRequestModel.getFirstName());
                    foundReader.setAddress(readerRequestModel.getAddress());
                    foundReader.setLastName(readerRequestModel.getLastName());
                    return foundReader;
                }))
                .flatMap(readerRepository::save) // Save the updated reader
                .map(EntityModelUtil::toReaderEntity); // Convert to response model
    }

    @Override
    public Mono<Void> suspendReaderAccount(String auth0UserID) {
        return readerRepository.findReaderByAuth0userId(auth0UserID)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("Reader not found with auth0UserId: " + auth0UserID))))
                .flatMap(reader -> {
                    reader.setAccountStatus(AccountStatus.SUSPENDED); // Update the status
                    return readerRepository.save(reader).then(); // Save the updated reader
                });
                 
    }


}
