package com.example.instagramfollowers.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FollowingDto(

    @JsonProperty("relationships_following")
    var relationshipsFollowings: List<RelationshipsFollowing> = emptyList()
)
