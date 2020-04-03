package org.jmb.travelhubagency.web

import org.jmb.travelhubagency.integration.persistence.AgencyRepository
import org.jmb.travelhubagency.model.Agency
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/agency")
class AgencyController(
        private val agencyRepository: AgencyRepository) {

    @GetMapping(produces = [ "text/event-stream" ])
    fun findAll() : Flux<Agency> = agencyRepository.findAll()

    @PostMapping
    fun create(@RequestBody agency: Agency) : Mono<Agency> = agencyRepository.save(agency)
}
