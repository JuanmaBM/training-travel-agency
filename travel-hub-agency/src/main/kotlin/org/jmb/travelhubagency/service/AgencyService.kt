package org.jmb.travelhubagency.service

import org.jmb.travelhubagency.integration.persistence.AgencyRepository
import org.jmb.travelhubagency.model.Agency
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.ValidationException

@Service
class AgencyService(
        private val agencyRepository: AgencyRepository) {

    fun create(agency: Agency): Mono<Agency> = validateAgency(agency).flatMap { agencyRepository.save(it) }
    fun findAll() : Flux<Agency> = agencyRepository.findAll()

    private fun validateAgency(agency: Agency): Mono<Agency> =
        if (agency == null) Mono.error(ValidationException("Agency must be defined"))
        else if (agency.hotels?.isEmpty() == false)
            throw ValidationException("Hotels must not been defined in creation process")
        else if (agency?.code == null || agency?.name == null)
            throw ValidationException("Code and name must be defined")
        else Mono.just(agency)
}
