package com.example.instagramfollowers.services

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.entity.Subscriber
import com.example.instagramfollowers.entity.User
import com.example.instagramfollowers.repositories.SubscriberRepository
import com.example.instagramfollowers.util.AppMsg
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * Contract
 */
interface FollowersReaderService {
    fun getNonReciprocalSubscribers(user: User, date: LocalDate): List<String>
    fun getNewUnSubscribers(user: User, subscriberType: SubscriberType): List<String>
}

/**
 * Implementation
 */
@Service
@Slf4j
class FollowersReaderServiceImpl(
    private val subscriberRepository: SubscriberRepository
) : FollowersReaderService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(FollowersReaderServiceImpl::class.java)
    }

    /**
     * We look for each "following" in the "followers" list, if we don’t find it,
     * then write it down to the "unSubscribers" list.
     */
    override fun getNonReciprocalSubscribers(user: User, date: LocalDate): List<String> {
        val followings = subscriberRepository
            .findAllByUserIdAndDateRecordingAndSubscriberType(user.id!!, date, SubscriberType.FOLLOWING)

        val followers = subscriberRepository
            .findAllByUserIdAndDateRecordingAndSubscriberType(user.id!!, date, SubscriberType.FOLLOWER)

        val result = compareSubscribers(followings, followers)

        LOGGER.info("\nfound unsubscribers: ${result.size}")
        return result.sortedBy { it }
    }

    override fun getNewUnSubscribers(user: User, subscriberType: SubscriberType): List<String> {
        val dates = subscriberRepository.getDateList2OrderByDesc()

        val newFollowings = subscriberRepository.findAllByUserIdAndDateRecordingAndSubscriberType(
            user.id!!, dates[0], subscriberType
        )
        val oldFollowings = subscriberRepository.findAllByUserIdAndDateRecordingAndSubscriberType(
            user.id!!, dates[1], subscriberType
        )

        val result = compareSubscribers(oldFollowings, newFollowings)
        LOGGER.info("\nfound new unsubscribers: ${result.size}")
        return result
    }

    /**
     * @param list1 - каждый элемент этого списка проверяем во втором, если не нашли -> записываем его в result
     * @param list2 - по какому списку будем искать
     */
    private fun compareSubscribers(list1: List<Subscriber>, list2: List<Subscriber>): List<String> {
        val result = arrayListOf<String>()
        list1.map { it1 ->
            val set = list2.filter { it2 -> it2.href == it1.href }.toSet()  // находим именно такого
            if (set.size > 1) {
                throw RuntimeException(AppMsg.FOLLOWER_UNIQUE_NOT_DUPLICATED)
            }
            if (set.isEmpty()) {    // если не нашли, значит - невзаимный подписчик
                result.add(it1.href)
            }
        }
        return result
    }
}
