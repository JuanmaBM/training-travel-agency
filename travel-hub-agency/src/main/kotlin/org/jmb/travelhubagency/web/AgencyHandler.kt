package org.jmb.travelhubagency.web

import org.jmb.travelhubagency.model.Agency
import org.jmb.travelhubagency.service.AgencyService
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.ValidationException

class AgencyHandler(private val agencyService: AgencyService) {

    fun findAll(request: ServerRequest) = ServerResponse.ok()
            .body(agencyService.findAll(), Agency::class.java)

    fun create(request: ServerRequest) = request.bodyToMono(Agency::class.java)
            .map { agencyService.create(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromPublisher(it, Agency::class.java)) }
            .onErrorResume { error ->
                when (error) {
                    is ValidationException -> ServerResponse.unprocessableEntity().bodyValue(error.localizedMessage)
                    else -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(error.localizedMessage)
                }
            }
}
