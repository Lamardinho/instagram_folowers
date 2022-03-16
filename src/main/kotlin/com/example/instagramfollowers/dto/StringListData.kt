package com.example.instagramfollowers.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Timestamp

data class StringListData(

    @JsonProperty("href")
    var href: String = "",

    @JsonProperty("value")
    var value: String = "",

    @JsonProperty("timestamp")
    var timestamp: Timestamp? = null
)
