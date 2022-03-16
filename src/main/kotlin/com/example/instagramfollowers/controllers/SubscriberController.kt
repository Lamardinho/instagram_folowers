package com.example.instagramfollowers.controllers

import com.example.instagramfollowers.dictionaries.SubscriberType
import com.example.instagramfollowers.dto.FollowersDto
import com.example.instagramfollowers.services.FollowersReaderService
import com.example.instagramfollowers.services.FollowersSaverService
import com.example.instagramfollowers.services.SubscribersReportService
import com.example.instagramfollowers.services.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@RequestMapping("/api/insta/subscribers")
class SubscriberController(
    private val followersSaverService: FollowersSaverService,
    private val followersReaderService: FollowersReaderService,
    private val subscribersReportService: SubscribersReportService,
    private val userService: UserService,
) {

    companion object {
        private const val LOGIN = "slezkin23"
    }

    @PostMapping("savefromfile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(description = "Read 2 files: followers and following, write to the database.")
    fun readFromFileAndSaveInDb(
        //  @RequestBody followersAndFollowingPathsDto: FollowersAndFollowingPathsDto,
        @RequestParam("followers") followers: MultipartFile,
        @RequestParam("followings") followings: MultipartFile
    ): String {
        return followersSaverService.readAndSaveInDB(
            userService.findByUserLogin(LOGIN),   //todo
            followers,
            followings,
            LocalDate.now()                      //todo
        )
    }

    @PostMapping("read_file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(description = "Read 2 files: followers and following")
    fun readFile(
        @RequestParam("followers") followers: MultipartFile,
        @RequestParam("followings") followings: MultipartFile
    ): FollowersDto {
        return subscribersReportService.readJsonFile(followers, followings)
    }

    @GetMapping("unsubscribers")
    @Operation(description = "Получить список невзаимных подписчиков")
    fun getNonReciprocalSubscribers(): List<String> {   //todo юзать LoginAndDateDto
        return followersReaderService.getNonReciprocalSubscribers(
            userService.findByUserLogin(LOGIN),   //todo
            LocalDate.now()
        )
    }

    @GetMapping("getNewUnSubscribers")
    @Operation(description = "Сравнить последную и предпоследную загрузку и выдать список новых отписчиков")
    fun getNewUnSubscribers(): List<String> {
        val result = followersReaderService.getNewUnSubscribers(
            userService.findByUserLogin(LOGIN), SubscriberType.FOLLOWER
        )
        result.map { println(it) }
        return result
    }

    @GetMapping("getNewUnSubscribersFromFriends")
    @Operation(description = "Сравнить последную и предпоследную загрузку и выдать список новых отписчиков из друзей")
    fun getNewUnSubscribersFromFriends(): List<String> {
        val result = followersReaderService.getNewUnSubscribers(
            userService.findByUserLogin(LOGIN), SubscriberType.FRIEND
        )
        result.map { println(it) }
        return result
    }
}
