package com.example.instagramfollowers.entity

import javax.persistence.*

/**
 * Не взаимно
 * */
@Entity
@Table(name = "a_not_mutual")
data class NotMutual(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    /** Имя */
    @Column(name = "value", length = 100)
    var value: String = "",
)
