package com.example.instagramfollowers.controllers

import com.example.instagramfollowers.dto.NotMutualDTO
import com.example.instagramfollowers.entity.Followers
import com.example.instagramfollowers.entity.Following
import com.example.instagramfollowers.entity.NotMutual
import com.example.instagramfollowers.repositories.FollowersRepository
import com.example.instagramfollowers.repositories.FollowingRepository
import com.example.instagramfollowers.repositories.NotMutualRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileReader

@RestController
@RequestMapping("/api/insta")
class InstaController(
    private val followersRepository: FollowersRepository,
    private val followingRepository: FollowingRepository,
    private val notMutualRepository: NotMutualRepository
) {
    @GetMapping("/read_from_db")
    fun readFromDb(): ArrayList<NotMutualDTO> {
        //notMutualRepository.deleteAll()
        val followingList = followingRepository.findAll()
        val notMutual = ArrayList<Following>()

        followingList.map {
            val check = followersRepository.findByValue(it.value)
            if (check == null) notMutual.add(it)
        }

        val result = ArrayList<NotMutualDTO>()
        notMutual.map {
            result.add(NotMutualDTO(id = it.id, user = it.value))
            //notMutualRepository.save(NotMutual(user = it.user))
        }

        return result
    }

    @GetMapping("read_from_file")
    fun readFromFile(): List<String> {
        val result = ArrayList<String>()
        // чистим всё
        followersRepository.deleteAll()
        followingRepository.deleteAll()
        notMutualRepository.deleteAll()

        val followersValues = readJson(filePath = FOLLOWERS_JSON, relationshipsType = FOLLOWERS)
        followersValues.map {
            followersRepository.save(Followers(value = it))
        }

        val followingValues = readJson(filePath = FOLLOWING_JSON, relationshipsType = FOLLOWING)
        val followingList = followingValues.map {
            followingRepository.save(Following(value = it))
        }

        followingList.map {
            val check = followersRepository.findByValue(it.value)
            if (check == null) {
                notMutualRepository.save(NotMutual(value = it.value))
            }
        }

        val list = notMutualRepository.findAll().sortedBy { it.value }

        list.map { result.add(it.value) }

        return result
    }

    private fun readJson(filePath: String, relationshipsType: String): ArrayList<String> {
        val result = ArrayList<String>()
        FileReader(filePath).use { file ->
            val obj = JSONParser().parse(file)
            val commonJsArray = JSONArray()
            commonJsArray.add(obj)

            commonJsArray.map { js ->
                val jsonObject = js as JSONObject
                val mainArray = jsonObject[relationshipsType] as JSONArray
                mainArray.map { map1 ->
                    val stringListDataObj = map1 as JSONObject
                    val stringListDataArray = stringListDataObj["string_list_data"] as JSONArray
                    stringListDataArray.map { sld ->
                        val valueObj = sld as JSONObject
                        result.add(valueObj["href"] as String)
                    }
                }
            }
        }
        return result
    }

    private companion object {
        private const val FOLLOWERS_JSON = "followers.json"
        private const val FOLLOWING_JSON = "following.json"
        private const val FOLLOWERS = "relationships_followers"
        private const val FOLLOWING = "relationships_following"
    }
}
