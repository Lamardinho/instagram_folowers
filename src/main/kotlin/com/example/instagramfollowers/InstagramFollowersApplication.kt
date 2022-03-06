package com.example.instagramfollowers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InstagramFollowersApplication

fun main(args: Array<String>) {
    runApplication<InstagramFollowersApplication>(*args)
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
