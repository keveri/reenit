package reenit.accesslink

import com.github.kittinunf.result.map
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import java.io.File

import reenit.accesslink.dataTypes.ApiCredentials
import reenit.accesslink.endpoint.TrainingData
import reenit.accesslink.endpoint.User

class Client(apiCredentials: ApiCredentials) {
    val user: User = User(apiCredentials)
    val trainingData: TrainingData = TrainingData(apiCredentials)
}

fun main(args: Array<String>) {
    val userId = Key("user.id", intType)
    val userToken = Key("user.token", stringType)
    val config = ConfigurationProperties.fromFile(File("user-info.properties"))
    val apiCreds = ApiCredentials(config[userId], config[userToken])

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
