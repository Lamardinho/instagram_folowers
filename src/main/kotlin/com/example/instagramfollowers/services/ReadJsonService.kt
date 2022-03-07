package com.example.instagramfollowers.services

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileReader

/**
 * Contract
 */
interface ReadJsonService {
    /**
     * @param file - путь к файлу
     * @param mainJsonObject - гланвый объект в котором находится список объектов
     * @param innerJsonObject - вложенные обхекты
     */
    fun readJsonFileAndFormingUrls(
        file: MultipartFile,
        mainJsonObject: String,
        innerJsonObject: String,
        objectField: String
    ): ArrayList<String>
}


/**
 * Implementation
 */
@Service
class ReadJsonServiceImpl : ReadJsonService {

    override fun readJsonFileAndFormingUrls(
        file: MultipartFile, mainJsonObject: String, innerJsonObject: String, objectField: String
    ): ArrayList<String> {
        val result = ArrayList<String>()

        var savedFile: File
        ByteArrayOutputStream().use {
            val filePath = "C:/Users/slezk/Downloads/jsonsSaved/" + file.originalFilename
            val checked = File(filePath)
            if (checked.exists()) {
                throw RuntimeException("Такой файл уже был загружен")   //todo
            }
            savedFile = File(filePath)
            file.transferTo(savedFile)
        }

        FileReader(savedFile).use {
            val obj = JSONParser().parse(it)
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
