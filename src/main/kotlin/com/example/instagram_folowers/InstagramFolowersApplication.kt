package com.example.instagram_folowers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InstagramFolowersApplication

fun main(args: Array<String>) {
    runApplication<InstagramFolowersApplication>(*args)
    println(appStarted())
    println("http://127.0.0.1:8085/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/")
}

fun appStarted(): String {
    return """  
 ▄▄▄·  ▄▄▄· ▄▄▄·    .▄▄ · ▄▄▄▄▄ ▄▄▄· ▄▄▄  ▄▄▄▄▄▄▄▄ .·▄▄▄▄  
▐█ ▀█ ▐█ ▄█▐█ ▄█    ▐█ ▀. •██  ▐█ ▀█ ▀▄ █·•██  ▀▄.▀·██▪ ██ 
▄█▀▀█  ██▀· ██▀·    ▄▀▀▀█▄ ▐█.▪▄█▀▀█ ▐▀▀▄  ▐█.▪▐▀▀▪▄▐█· ▐█▌
▐█ ▪▐▌▐█▪·•▐█▪·•    ▐█▄▪▐█ ▐█▌·▐█ ▪▐▌▐█•█▌ ▐█▌·▐█▄▄▌██. ██ 
 ▀  ▀ .▀   .▀        ▀▀▀▀  ▀▀▀  ▀  ▀ .▀  ▀ ▀▀▀  ▀▀▀ ▀▀▀▀▀•                                                                                         
"""
}
