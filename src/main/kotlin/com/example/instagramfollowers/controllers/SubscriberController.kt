package com.example.instagramfollowers.controllers

import com.example.instagramfollowers.dto.FollowersAndFollowingPathsDto
import com.example.instagramfollowers.services.FollowersService
import com.example.instagramfollowers.services.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

/**
 * Getting a list of non-reciprocal subscribers.
 */
@RestController
@RequestMapping("/api/insta/subscribers")
class SubscriberController(
    private val followersService: FollowersService,
    private val userService: UserService
) {
    /**
     * Read 2 files: "followers" and "following", write to the database.
     */
    @PostMapping("savefromfile")
    @Operation(description = "read and save all subscribers from files")
    fun readFromFileAndSaveInDb(@RequestBody followersAndFollowingPathsDto: FollowersAndFollowingPathsDto): String {
        return followersService.readAndSaveInDB(
            userService.findByUserLogin("slezkin23"),   //todo
            followersAndFollowingPathsDto.followersPath,
            followersAndFollowingPathsDto.followingsPath
        )
    }

    /**
     * Получить список невзаимных подписчиков
     */
    @GetMapping("unsubscribers")
    @Operation(description = "get non-reciprocal subscribers")
    fun getNonReciprocalSubscribers(): List<String> {   //todo юзать LoginAndDateDto
        return followersService.getNonReciprocalSubscribers(
            userService.findByUserLogin("slezkin23"),
            LocalDate.now()
        )
    }
}
