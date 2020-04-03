package org.jmb.travelhubagency.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Hotel (
        @Id @JsonIgnore val _id: ObjectId?,
        val hotelCode: String,
        val commission: Double)
