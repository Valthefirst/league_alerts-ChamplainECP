package com.calerts.computer_alertsbe.utils.dataLoaders;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AuthorDataLoaderService implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {

        Author author1 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("3b63de68-9161-4925-b38b-e686dd88f848"))
                .emailAddress("dupont.nicky@email.com")
                .firstName("Nicky")
                .lastName("Dupont")
                .biography(new Biography("Nicky Dupont is a NBA enthusiast and met Micheal Jordan when he just " +
                        "started out.", 15))
                .articles(new ArticleList(List.of(new ArticleIdentifier("e09e8812-32fb-434d-908f-40d5e3b137ca"),
                        new ArticleIdentifier("806d087b-e708-4293-925b-40b2972741e5"))))
                .build();

        Author author2 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("2b23d817-acc3-494f-bdeb-28a002308188"))
                .emailAddress("chan.jack@email.com")
                .firstName("Jack")
                .lastName("Chan")
                .biography(new Biography())
                .articles(new ArticleList())
                .build();

        Author author3 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("5a87fa4e-f2a4-4874-b7f6-c22c29decb97"))
                .emailAddress("michael.jones@email.com")
                .firstName("Michael")
                .lastName("Jones")
                .biography(new Biography("Former professional basketball player, now coaching youth teams and " +
                        "mentoring athletes.", 11))
                .articles(new ArticleList())
                .build();

        Author author4 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("6d2bb1f9-0c4f-4691-a6f5-5599e7f2068c"))
                .emailAddress("lily.williams@email.com")
                .firstName("Lily")
                .lastName("Williams")
                .biography(new Biography("Professional tennis player turned coach, specializing in women's singles " +
                        "and doubles.", 11))
                .articles(new ArticleList())
                .build();

        Author author5 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("7e93579d-cf40-44a0-9b82-6f8b05f3185b"))
                .emailAddress("george.smith@email.com")
                .firstName("George")
                .lastName("Smith")
                .biography(new Biography("Football coach with over 20 years of experience, now working with " +
                        "under-18 teams to develop future stars.", 17))
                .articles(new ArticleList(List.of(new ArticleIdentifier("ca1d0478-6a9c-421b-b815-84965e3c7b4a"))))
                .build();

        Author author6 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("8f5be0c1-0fc1-4cfb-b7b8-fc46e221d31e"))
                .emailAddress("nancy.davis@email.com")
                .firstName("Nancy")
                .lastName("Davis")
                .biography(new Biography("Former Olympic gymnast, now a motivational speaker and advocate for " +
                        "mental health in sports.", 14))
                .articles(new ArticleList())
                .build();

        Author author7 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("9a21d4f8-85fa-477b-bc0a-76819b2d7c8f"))
                .emailAddress("kevin.lee@email.com")
                .firstName("Kevin")
                .lastName("Lee")
                .biography(new Biography("Ex-professional boxer and trainer, now working on youth programs to " +
                        "promote fitness and discipline.", 14))
                .articles(new ArticleList())
                .build();

        Author author8 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("1b71f87d-d5c7-47c2-90e5-0e11b83ed978"))
                .emailAddress("elizabeth.brown@email.com")
                .firstName("Elizabeth")
                .lastName("Brown")
                .biography(new Biography("Baseball analyst and commentator, known for breaking down game strategies" +
                        " and player performance.", 13))
                .articles(new ArticleList())
                .build();

        Author author9 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("1c35b82c-d9a3-4f8f-a0a6-bde7a5096017"))
                .emailAddress("thomas.clark@email.com")
                .firstName("Thomas")
                .lastName("Clark")
                .biography(new Biography("Retired soccer player turned coach, focusing on player development and " +
                        "sports leadership.", 12))
                .articles(new ArticleList())
                .build();

        Author author10 = Author.builder()
                .authorIdentifier(new AuthorIdentifier("2d87fa3e-a1b1-4b7d-b5b6-c33982a8728c"))
                .emailAddress("rebecca.martin@email.com")
                .firstName("Rebecca")
                .lastName("Martin")
                .biography(new Biography("Professional swimmer, now mentoring young swimmers and advocating for " +
                        "water safety.", 11))
                .articles(new ArticleList())
                .build();


        Flux.just(author1, author2, author3, author4, author5, author6, author7, author8, author9, author10)
                .flatMap(s -> authorRepository.insert(Mono.just(s))
                        .log(s.toString()))
                .subscribe();

    }
}
