package org.jmb.travelhubhotel.domain

import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

class Reservation(
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long?,
        @ManyToOne val room: Room,
        val nif: String,
        val userName: String,
        val checking: LocalDate,
        val checkout: LocalDate)
