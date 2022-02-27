package com.example.instagramfollowers.services

import com.example.instagramfollowers.entity.User
import com.example.instagramfollowers.repositories.UserRepository
import com.example.instagramfollowers.util.AppMsg
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Contract
 */
interface UserService {
    fun findByUserId(userId: Long): User
    fun findByUserLogin(login: String): User
}

/**
 * Implementation
 */
@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun findByUserId(userId: Long): User {
        return userRepository.findById(userId).orElseThrow { RuntimeException(AppMsg.USER_NOT_FOUND) }
    }

    override fun findByUserLogin(login: String): User {
        return userRepository.findByLogin(login).orElseThrow { RuntimeException(AppMsg.USER_NOT_FOUND) }
    }
}
