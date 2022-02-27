package com.example.instagramfollowers.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @NotBlank
    @Column(name = "login", length = 100, unique = true)
    var login: String? = null,

    @NotBlank
    @Email
    @Column(name = "email", length = 100, unique = true)
    var email: String? = null,
)
