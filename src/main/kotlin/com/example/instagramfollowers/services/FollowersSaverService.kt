package com.example.instagramfollowers.services

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.entity.Subscriber
import com.example.instagramfollowers.entity.User
import com.example.instagramfollowers.repositories.SubscriberRepository
import com.example.instagramfollowers.util.AppConstants
import com.example.instagramfollowers.util.AppMsg
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Contract
 */
interface FollowersSaverService {
    fun readAndSaveInDB(user: User, followersPath: String, followingPath: String, commonDate: LocalDate): String
}


/**
 * Implementation
 */
@Service
@Slf4j
class FollowersSaverServiceImpl(
    private val subscriberRepository: SubscriberRepository,
    private val readJsonService: ReadJsonService,
) : FollowersSaverService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(FollowersSaverServiceImpl::class.java)
    }

    @Transactional
    override fun readAndSaveInDB(
        user: User, followersPath: String, followingPath: String, commonDate: LocalDate
    ): String {
        // forming and save list of "followers"
        val followers = commonReadAndSave(
            followersPath, AppConstants.RELATIONSHIPS_FOLLOWERS, user, commonDate, SubscriberType.FOLLOWER
        )
        // forming and save list of "following"
        val followings = commonReadAndSave(
            followingPath, AppConstants.RELATIONSHIPS_FOLLOWING, user, commonDate, SubscriberType.FOLLOWING
        )

        val unsubscribers = arrayListOf<String>()
        val friends = arrayListOf<String>()
        followings.map { following ->
            val set = followers.filter { it.href == following.href }.toSet()  // находим именно такого
            if (set.size > 1) {
                throw RuntimeException(AppMsg.FOLLOWER_UNIQUE_NOT_DUPLICATED)
            }
            if (set.isEmpty()) {    // если не нашли, значит - невзаимный подписчик
                val unsubscriber = saveSubscriber(user, following.href, commonDate, SubscriberType.UNSUBSCRIBER)
                unsubscribers.add(unsubscriber.href)
            } else {
                val friend = saveSubscriber(user, following.href, commonDate, SubscriberType.FRIEND)
                friends.add(friend.href)
            }
        }

        val info = "" +
                "\nSaved followers: ${followers.size}, " +
                "\nsaved followings: ${followings.size} , " +
                "\nsaved unsubscribers: ${unsubscribers.size} , " +
                "\nsaved friends: ${friends.size}"
        LOGGER.info(info)
        return info
    }

    private fun commonReadAndSave(
        filePath: String,
        mainDataArrayName: String,
        user: User,
        date: LocalDate,
        subscriberType: SubscriberType
    ): List<Subscriber> {
        // forming and save list of "followers"
        val followers = readJsonService.readJsonFileAndFormingUrls(
            filePath = filePath,
            mainJsonObject = mainDataArrayName,
            innerJsonObject = AppConstants.STRING_LIST_DATA,
            objectField = AppConstants.HREF
        ).map { Subscriber(user = user, href = it, dateRecording = date, subscriberType = subscriberType) }
        return subscriberRepository.saveAll(followers)
    }

    private fun saveSubscriber(
        user: User,
        href: String,
        date: LocalDate,
        subscriberType: SubscriberType
    ): Subscriber {
        return subscriberRepository.save(
            Subscriber(user = user, href = href, dateRecording = date, subscriberType = subscriberType)
        )
    }
}
