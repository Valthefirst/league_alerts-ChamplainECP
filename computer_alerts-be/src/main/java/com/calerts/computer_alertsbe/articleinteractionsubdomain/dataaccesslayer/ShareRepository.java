package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ShareRepository extends ReactiveMongoRepository<Share, String> {

}
