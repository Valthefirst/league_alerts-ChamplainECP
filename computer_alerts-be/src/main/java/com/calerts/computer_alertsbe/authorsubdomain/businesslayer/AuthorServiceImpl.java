package com.calerts.computer_alertsbe.authorsubdomain.businesslayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorRepository;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<AuthorResponseModel> getAllAuthors() {
        return authorRepository.findAll()
                .map(EntityModelUtil::toAuthorResponseModel);
    }

    @Override
    public Mono<AuthorResponseModel> getAuthor(String authorId) {
        return authorRepository.findAuthorByAuthorIdentifier_AuthorId(authorId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("Author id not found: " + authorId))))
                .map(EntityModelUtil::toAuthorResponseModel);
    }
}
