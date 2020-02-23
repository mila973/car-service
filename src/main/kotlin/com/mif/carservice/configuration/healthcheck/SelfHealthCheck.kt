package io.inventi.emails.config.healthcheck

import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.stereotype.Component

@Component("self")
class SelfHealthCheck : AbstractHealthIndicator() {
    override fun doHealthCheck(builder: Health.Builder) {
        builder.up().build()
    }
}