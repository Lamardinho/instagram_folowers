package com.example.instagramfollowers.repositories

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SubscriberRepository : JpaRepository<Subscriber, Long> {

    fun findAllByUserIdAndDateRecordingAndSubscriberType(
        userId: Long,
        date: LocalDate,
        subscriberType: SubscriberType
    ): List<Subscriber>

    @Query(
        "select distinct s.dateRecording from Subscriber s order by s.dateRecording asc"   //todo сделать по Юзеру
    )
    fun getDateList2OrderByAsc(): List<LocalDate>

    @Query(
        "select distinct s.dateRecording from Subscriber s order by s.dateRecording desc"   //todo сделать по Юзеру
    )
    fun getDateList2OrderByDesc(): List<LocalDate>
}
