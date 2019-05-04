package reenit.accesslink

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.annotations.SerializedName

data class UserExtraInfo(
        val value: String,
        val index: Int,
        val name: String
)

data class User(
        @SerializedName("polar-user-id")
        val polarUserId: String,
        @SerializedName("member-id")
        val memberId: String,
        @SerializedName("registration-date")
        val registrationDate: String,
        @SerializedName("first-name")
        val firstName: String,
        @SerializedName("last-name")
        val lastName: String,
        val birthdate: String,
        val gender: String,
        val weight: Float,
        val height: Float,
        val field: Array<UserExtraInfo>
)


fun register(userInfo: UserInfo): Result<User, FuelError> {
    val data = "{\"member-id\": \"${userInfo.id}\"}"
    val (_, response, result) =
            Fuel.post("https://www.polaraccesslink.com/v3/users")
                    .body(data)
                    .appendHeader("Content-Type", "application/json")
                    .appendHeader("Accept", "application/json")
                    .appendHeader("Authorization", "Basic ${userInfo.token}")
                    .responseObject<User>()
    println(response)
    println(result)
    return result
}

fun delete(userInfo: UserInfo) {
    val (_, response, result) =
            Fuel.delete("https://www.polaraccesslink.com/v3/users/${userInfo.id}")
                    .appendHeader("Authorization", "Basic ${userInfo.token}")
                    .responseString()
    println(response)
}
