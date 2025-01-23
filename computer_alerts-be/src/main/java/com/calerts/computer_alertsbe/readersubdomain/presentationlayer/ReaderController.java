package com.calerts.computer_alertsbe.readersubdomain.presentationlayer;



import com.calerts.computer_alertsbe.readersubdomain.businesslayer.ReaderService;
import com.calerts.computer_alertsbe.utils.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/readers")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }
    @GetMapping()
    public Flux<ReaderResponseModel> getReaders() {
        return readerService.getReaders();
    }

    @GetMapping(value = "/{auth0UserId}")
    public Mono<ResponseEntity<ReaderResponseModel>> getReaderbyAuth0UserId(@PathVariable  String auth0UserId) {
     return Mono.just(auth0UserId)
             .switchIfEmpty(Mono.error(new InvalidInputException(auth0UserId)))
             .flatMap(readerService::getReaderByAuth0UserID)
             .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/{auth0UserId}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ReaderResponseModel>> updateReaderDetails (@RequestBody Mono<ReaderRequestModel> readerRequestModel, @PathVariable  String auth0UserId) {
        return Mono.just(auth0UserId)
                .switchIfEmpty(Mono.error(new InvalidInputException("Provided Body is no good" + auth0UserId)))
                .flatMap(providedAuth0UserId -> readerService.updateReaderDetails(auth0UserId,readerRequestModel))
                .map(ResponseEntity:: ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
