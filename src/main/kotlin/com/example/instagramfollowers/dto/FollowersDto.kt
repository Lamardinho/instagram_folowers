package com.example.instagramfollowers.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FollowersDto(

    @JsonProperty("relationships_followers")
    var relationshipsFollowers: List<RelationshipsFollower> = emptyList()
)
