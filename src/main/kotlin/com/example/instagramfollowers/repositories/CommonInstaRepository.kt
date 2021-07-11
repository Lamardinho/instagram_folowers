package com.example.instagramfollowers.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.instagramfollowers.entity.Followers
import com.example.instagramfollowers.entity.Following
import com.example.instagramfollowers.entity.NotMutual

@Repository
interface FollowersRepository : JpaRepository<Followers, Long> {
    fun findByValue(value: String): Followers?
}

@Repository
interface FollowingRepository : JpaRepository<Following, Long>

@Repository
interface NotMutualRepository : JpaRepository<NotMutual, Long>
