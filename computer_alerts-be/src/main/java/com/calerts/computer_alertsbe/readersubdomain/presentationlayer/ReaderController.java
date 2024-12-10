package com.calerts.computer_alertsbe.readersubdomain.presentationlayer;



import com.calerts.computer_alertsbe.readersubdomain.businesslayer.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
}
