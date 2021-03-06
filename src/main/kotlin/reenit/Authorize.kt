package reenit

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import spark.kotlin.Http
import spark.kotlin.ignite
import java.io.File

data class AccessTokenResponse(
    val access_token: String,
    val x_user_id: Int,
    val token_type: String,
    val expires_in: Int
)

fun getAccessToken(api_id: String, api_key: String, code: String): Result<AccessTokenResponse, FuelError> {
    val (_, _, result) =
            Fuel.post("https://polarremote.com/v2/oauth2/token")
                    .authentication()
                    .basic(api_id, api_key)
                    .appendHeader("Content-Type", "application/x-www-form-urlencoded")
                    .appendHeader("Accept", "application/json;charset=UTF-8")
                    .body("grant_type=authorization_code&code=$code")
                    .responseObject<AccessTokenResponse>()
    return result
}

fun main(args: Array<String>) {
    val api_id = Key("api.id", stringType)
    val api_key = Key("api.key", stringType)
    val config = ConfigurationProperties.fromResource("api.properties")

    val authUrl = "https://flow.polar.com/oauth2/authorization?response_type=code&client_id=${config[api_id]}"
    val http: Http = ignite()
    http.port(5000)

    http.redirect.get("/", authUrl)

    http.get("/oauth2_callback") {
        val code = request.queryParams("code")
        val tokenResponse = getAccessToken(config[api_id], config[api_key], code)

        tokenResponse.fold({ value ->
            Thread { Thread.sleep(1000); http.stop() }.start()
            println("Success: Access token and user id written to config file")
            File("user-info.properties").printWriter().use { out ->
                out.println("user.id=${value.x_user_id}")
                out.println("user.token=${value.access_token}")
            }
            "Next run the client"
        }, { error ->
            "Error: $error"
        })
    }

    println("Go to: http://localhost:5000/")
}
