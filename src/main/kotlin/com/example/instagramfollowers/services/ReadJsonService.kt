package com.example.instagramfollowers.services

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.stereotype.Service
import java.io.FileReader

/**
 * Contract
 */
interface ReadJsonService {
    /**
     * @param filePath - путь к файлу
     * @param mainJsonObject - гланвый объект в котором находится список объектов
     * @param innerJsonObject - вложенные обхекты
     */
    fun readJsonFileAndFormingUrls(
        filePath: String, mainJsonObject: String, innerJsonObject: String, objectField: String
    ): ArrayList<String>
}


/**
 * Implementation
 */
@Service
class ReadJsonServiceImpl : ReadJsonService {

    override fun readJsonFileAndFormingUrls(
        filePath: String, mainJsonObject: String, innerJsonObject: String, objectField: String
    ): ArrayList<String> {
        val result = ArrayList<String>()
        FileReader(filePath).use { file ->
            val obj = JSONParser().parse(file)
            val commonJsArray = JSONArray()
            commonJsArray.add(obj)

            commonJsArray.map { js ->
                val jsonObject = js as JSONObject
                val mainDataArray = jsonObject[mainJsonObject] as JSONArray
                mainDataArray.map { map1 ->
                    val stringListDataObj = map1 as JSONObject
                    val stringListDataArray = stringListDataObj[innerJsonObject] as JSONArray
                    stringListDataArray.map { sld ->
                        val valueObj = sld as JSONObject
                        result.add(valueObj[objectField] as String)
                    }
                }
            }
        }
        return result
    }
}
