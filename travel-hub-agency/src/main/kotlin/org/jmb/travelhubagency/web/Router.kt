package org.jmb.travelhubagency.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

fun router(agencyHandler: AgencyHandler) = router {
    accept(MediaType.APPLICATION_JSON).nest {
        "/agency".nest {
            GET("/", agencyHandler::findAll)
            POST("/", agencyHandler::create)
        }
    }
}
