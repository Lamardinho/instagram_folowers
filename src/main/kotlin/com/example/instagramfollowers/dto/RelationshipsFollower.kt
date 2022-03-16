package com.example.instagramfollowers.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RelationshipsFollower(

    @JsonProperty("title")
    var title: String = "",

    @JsonProperty("media_list_data")
    var mediaListData: List<MediaListData> = emptyList(),

    @JsonProperty("string_list_data")
    var stringListData: List<StringListData> = emptyList()
)
