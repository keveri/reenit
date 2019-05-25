package reenit.accesslink

import com.github.kittinunf.result.map
import com.natpryce.konfig.*
import java.io.File

import reenit.accesslink.dataTypes.UserInfo
import reenit.accesslink.endpoint.*

class Client(val userInfo: UserInfo) {
    val user: User = User(userInfo)
    val trainingData: TrainingData = TrainingData(userInfo)
}

fun main(args: Array<String>) {
    val user_id    = Key("user.id", intType)
    val user_token = Key("user.token", stringType)
    val config     = ConfigurationProperties.fromFile(File("user-info.properties"))
    val userInfo   = UserInfo(config[user_id], config[user_token])

    val client = Client(userInfo)

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
