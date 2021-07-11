package com.example.instagramfollowers.entity

import javax.persistence.*

/**
 * За кем следишь
 * */
@Entity
@Table(name = "a_following")
data class Following(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    /** Имя */
    @Column(name = "value", length = 100)
    var value: String = "",
)
