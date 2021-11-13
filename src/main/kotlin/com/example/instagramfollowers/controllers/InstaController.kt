package com.example.instagramfollowers.controllers

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

/**
 * Getting a list of non-reciprocal subscribers.
 */
@RestController
@RequestMapping("/api/insta")
class InstaController(
    private val followersRepository: FollowersRepository,
    private val followingRepository: FollowingRepository,
    private val notMutualRepository: NotMutualRepository
) {
    /**
     * Read 2 files: "followers" and "following", write to the database and get a list of motherfuckers.
     */
    @GetMapping("read_from_file")
    fun readFromFile(): Set<String> {
        val result = ArrayList<String>()
        // clear all tables in database
        followersRepository.deleteAll()
        followingRepository.deleteAll()
        notMutualRepository.deleteAll()

        // forming and save list of "followers"
        val followersValues = readJsonFileAndFormingUrls(filePath = FOLLOWERS_JSON, relationshipsType = FOLLOWERS)
        followersValues.map {
            followersRepository.save(Followers(value = it))
        }

        // forming and save list of "following"
        val followingValues = readJsonFileAndFormingUrls(filePath = FOLLOWING_JSON, relationshipsType = FOLLOWING)
        val followingList = followingValues.map {
            followingRepository.save(Following(value = it))
        }

        // we look for each "following" in the "followers" list, if we don’t find it, then write it down to the "motherfuckers" list.
        followingList.map {
            val check = followersRepository.findByValue(it.value)
            if (check == null) {
                notMutualRepository.save(NotMutual(value = it.value))
            }
        }

        val list = notMutualRepository.findAll().sortedBy { it.value }

        list.map { result.add(it.value) }

        return result.toSet()
    }

    private fun readJsonFileAndFormingUrls(filePath: String, relationshipsType: String): ArrayList<String> {
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
                    val stringListDataArray = stringListDataObj[STRING_LIST_DATA] as JSONArray
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
        private const val STRING_LIST_DATA = "string_list_data"
    }
}
