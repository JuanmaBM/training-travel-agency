package org.jmb.travelhubhotel.domain

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

class Hotel(
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long?,
        val code: String,
        val description: String,
        @OneToMany(mappedBy = "room_id") val rooms: List<Room>)
