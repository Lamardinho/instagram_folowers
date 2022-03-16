package com.example.instagramfollowers.services

import com.example.instagramfollowers.dto.FollowersDto
import com.example.instagramfollowers.dto.FollowingDto
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * Contract
 */
interface SubscribersReportService {
    fun readJsonFile(followersFile: MultipartFile, followingFile: MultipartFile): FollowersDto
}

/**
 * Implementation
 */
@Service
@Slf4j
class SubscribersReportServiceImpl(
    private val objectMapper: ObjectMapper
) : SubscribersReportService {

    override fun readJsonFile(followersFile: MultipartFile, followingFile: MultipartFile): FollowersDto {
        val followers = objectMapper.readValue(followersFile.bytes, FollowersDto::class.java)
        val following = objectMapper.readValue(followingFile.bytes, FollowingDto::class.java)

        return FollowersDto()
    }
}
