package com.example.instagramfollowers.services

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.entity.Subscriber
import com.example.instagramfollowers.entity.User
import com.example.instagramfollowers.repositories.SubscriberRepository
import com.example.instagramfollowers.util.AppConstants
import com.example.instagramfollowers.util.AppMsg
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Contract
 */
interface FollowersService {

    fun readAndSaveInDB(user: User, followersPath: String, followingPath: String): String

    fun getNonReciprocalSubscribers(user: User, date: LocalDate): List<String>
}


/**
 * Implementation
 */
@Service
@Transactional
class FollowersServiceImpl(
    private val subscriberRepository: SubscriberRepository,
    private val readJsonService: ReadJsonService,
) : FollowersService {

    override fun readAndSaveInDB(user: User, followersPath: String, followingPath: String): String {
        val commonDate = LocalDate.now()

        // forming and save list of "followers"
        val followers = commonReadAndSave(
            followersPath, AppConstants.RELATIONSHIPS_FOLLOWERS, user, commonDate, SubscriberType.FOLLOWER
        )
        // forming and save list of "following"
        val followings = commonReadAndSave(
            followingPath, AppConstants.RELATIONSHIPS_FOLLOWING, user, commonDate, SubscriberType.FOLLOWING
        )

        return "Saved followers: ${followers.size}, saved followings: ${followings.size}"
    }

    /**
     * We look for each "following" in the "followers" list, if we don’t find it,
     * then write it down to the "unSubscribers" list.
     */
    override fun getNonReciprocalSubscribers(user: User, date: LocalDate): List<String> {
        val result = ArrayList<String>()

        val followings = subscriberRepository
            .findAllByUserIdAndDateRecordingAndSubscriberType(user.id!!, date, SubscriberType.FOLLOWING)

        val followers = subscriberRepository
            .findAllByUserIdAndDateRecordingAndSubscriberType(user.id!!, date, SubscriberType.FOLLOWER)

        followings.map { following ->
            val set = followers.filter { it.href == following.href }.toSet()  // находим именно такого
            if (set.size > 1) {
                throw RuntimeException(AppMsg.FOLLOWER_UNIQUE_NOT_DUPLICATED)
            }
            if (set.isEmpty()) {    // если не нашли, значит - невзаимный подписчик
                result.add(following.href)
            }
        }
        return result.sortedBy { it }
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
}
