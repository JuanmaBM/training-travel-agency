package org.jmb.travelhubhotel.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Room(
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long?,
        val code: String,
        val name: String,
        val description: String)
