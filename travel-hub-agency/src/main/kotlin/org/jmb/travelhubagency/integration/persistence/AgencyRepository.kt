package org.jmb.travelhubagency.integration.persistence

import org.bson.types.ObjectId
import org.jmb.travelhubagency.model.Agency
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface AgencyRepository : ReactiveMongoRepository<Agency, ObjectId> {
    fun findByCode(agencyCode: String) : Mono<Agency>
}
