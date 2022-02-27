package com.example.instagramfollowers.repositories

import com.example.instagramfollowers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByLogin(login: String): Optional<User>
}
