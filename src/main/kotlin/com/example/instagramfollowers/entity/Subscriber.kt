package com.example.instagramfollowers.entity

import com.example.instagramfollowers.dictionaries.SubscriberType
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Не взаимно
 * */
@Entity
@Table(name = "subscribers")
class Subscriber(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    var user: User? = null,

    @NotBlank
    @Column(name = "value", length = 100)
    var href: String = "",

    /** Дата записи */
    @Column(name = "date_recording")
    @NotNull
    val dateRecording: LocalDate? = null,

    /** Категория */
    @Column(name = "subscriber_type", length = 100)
    @Enumerated(EnumType.STRING)
    var subscriberType: SubscriberType? = null,
)
