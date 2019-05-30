package reenit

import com.github.kittinunf.result.map
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import java.io.File

import reenit.accesslink.Client
import reenit.accesslink.dataTypes.ApiCredentials

fun readApiCreds(filename: String): ApiCredentials {
    val userId = Key("user.id", intType)
    val userToken = Key("user.token", stringType)
    val config = ConfigurationProperties.fromFile(File(filename))
    return ApiCredentials(config[userId], config[userToken])
}

fun main(args: Array<String>) {
    val apiCreds = readApiCreds("user-info.properties")
    val client = Client(apiCreds)

    val userInfoResult = client.user.info()
    userInfoResult.map { println(it) }

    client.trainingData.createTransaction().map { println(it) }

    client.trainingData.listExercises().fold({ value ->
        println("Data:")
        println(value.map { it.toString() })
    }, { error ->
        println("Error: $error")
    })
}
