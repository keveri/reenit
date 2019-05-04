package reenit.accesslink

import com.natpryce.konfig.*
import java.io.File

fun main(args: Array<String>) {
    println("hello")
    val user_id    = Key("user.id", intType)
    val user_token = Key("user.token", stringType)
    val config     = ConfigurationProperties.fromFile(File("user-info.properties"))
    val userInfo   = UserInfo(config[user_id], config[user_token])

    // 401: old token
    // 200: created
    val userResult = register(userInfo)

    listExercises(userInfo).fold({ value ->
        println("Data:")
        println(value.map { it.toString() })
    }, { error ->
        println("Error: $error")
    })

    //204: deleted
    delete(userInfo)
}
