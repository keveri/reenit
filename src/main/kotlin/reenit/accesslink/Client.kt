package reenit.accesslink

import com.github.kittinunf.result.map
import com.natpryce.konfig.*
import reenit.accesslink.endpoint.*
import java.io.File

class Client(val userInfo: UserInfo) {
    val user: User = User(userInfo)
}

fun main(args: Array<String>) {
    val user_id    = Key("user.id", intType)
    val user_token = Key("user.token", stringType)
    val config     = ConfigurationProperties.fromFile(File("user-info.properties"))
    val userInfo   = UserInfo(config[user_id], config[user_token])

    val client = Client(userInfo)

    val userInfoResult = client.user.info()
    userInfoResult.map { println(it) }

    createTransaction(userInfo).map { println(it) }

    listExercises(userInfo).fold({ value ->
        println("Data:")
        println(value.map { it.toString() })
    }, { error ->
        println("Error: $error")
    })
}
