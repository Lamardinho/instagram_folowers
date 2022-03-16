package com.example.instagramfollowers.services

import com.example.instagramfollowers.dto.FollowersDto
import com.example.instagramfollowers.dto.FollowingDto
import com.example.instagramfollowers.util.AppMsg
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * Contract
 */
interface SubscribersReportService {

    fun readJsonFilesAndGetNonReciprocalSubscribers(
        followersFile: MultipartFile,
        followingFile: MultipartFile
    ): List<String>
}

/**
 * Implementation
 */
@Service
@Slf4j
class SubscribersReportServiceImpl(
    private val objectMapper: ObjectMapper
) : SubscribersReportService {

    override fun readJsonFilesAndGetNonReciprocalSubscribers(
        followersFile: MultipartFile,
        followingFile: MultipartFile
    ): List<String> {
        val followers = objectMapper.readValue(followersFile.bytes, FollowersDto::class.java)
        val following = objectMapper.readValue(followingFile.bytes, FollowingDto::class.java)

        val followersHref = followers.relationshipsFollowers.map { it.stringListData[0].href }
        val followingHref = following.relationshipsFollowings.map { it.stringListData[0].href }

        val result = compareSubscribers(followingHref, followersHref)
        result.forEach { println(it) }
        return result
    }

    /**
     * @param list1 - каждый элемент этого списка проверяем во втором, если не нашли -> записываем его в result
     * @param list2 - по какому списку будем искать
     */
    private fun compareSubscribers(list1: List<String>, list2: List<String>): List<String> {
        val result = arrayListOf<String>()
        list1.map { it1 ->
            val set = list2.filter { it2 -> it2 == it1 }.toSet()  // находим именно такого
            if (set.size > 1) {
                throw RuntimeException(AppMsg.FOLLOWER_UNIQUE_NOT_DUPLICATED)
            }
            if (set.isEmpty()) {    // если не нашли, значит - невзаимный подписчик
                result.add(it1)
            }
        }
        return result
    }
}
