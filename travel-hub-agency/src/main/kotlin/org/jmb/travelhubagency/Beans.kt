package org.jmb.travelhubagency

import org.jmb.travelhubagency.web.AgencyHandler
import org.jmb.travelhubagency.web.router
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

class Beans : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) = beans {
        bean { AgencyHandler(ref()) }
        bean { router(ref()) }
    }.initialize(context)
}
