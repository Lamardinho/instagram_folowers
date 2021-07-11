package com.example.instagramfollowers.entity

import javax.persistence.*

/**
 * Подписчики
 * */
@Entity
@Table(name = "a_followers")
data class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    /** Имя */
    @Column(name = "value", length = 100)
    var value: String = "",
)
