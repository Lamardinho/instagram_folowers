package com.example.instagramfollowers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class LoginAndDateDto(

    //  val userLogin: String,

    @JsonFormat(pattern = "YYYY-MM-dd")
    val date: LocalDate
)
