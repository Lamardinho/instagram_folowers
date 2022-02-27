package com.example.instagramfollowers.repositories

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SubscriberRepository : JpaRepository<Subscriber, Long> {

    fun findAllByUserIdAndDateRecordingAndSubscriberType(
        userId: Long,
        date: LocalDate,
        subscriberType: SubscriberType
    ): List<Subscriber>
}
