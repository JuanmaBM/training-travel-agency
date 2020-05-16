package org.jmb.travelhubagency.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "agency")
data class Agency (
        @Id @JsonIgnore val _id: ObjectId?,
        val code: String,
        val name: String,
        val hotels: List<Hotel>?)
