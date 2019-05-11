package reenit.accesslink.endpoint

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.annotations.SerializedName

data class UserInfo(
        val id: Int,
        val token: String
)

data class HeartRate(
        val average: Int,
        val maximum: Int
)

data class Exercise(
        val id: Int,
        @SerializedName("upload-time")
        val uploadTime: String,
        @SerializedName("polar-user")
        val polarUser: String,
        val device: String,
        @SerializedName("start-time")
        val startTime: String,
        val duration: String,
        val calories: Int,
        val distance: Float,
        @SerializedName("heart-rate")
        val heartRate: HeartRate,
        @SerializedName("training-load")
        val trainingLoad: Float,
        val sport: String,
        @SerializedName("has-route")
        val hasRoute: Boolean,
        @SerializedName("club-id")
        val clubId: Int,
        @SerializedName("club-name")
        val clubName: String,
        @SerializedName("detailed-sport-info")
        val detailedSportInfo: String
)

typealias Exercises = Array<Exercise>

data class TransactionLocation(
        @SerializedName("transaction-id")
        val transactionId: Int,
        @SerializedName("resource-url")
        val resourceUrl: String
)

fun createTransaction(userInfo: UserInfo): Result<TransactionLocation, FuelError> {
    val path = "https://www.polaraccesslink.com/v3/users/${userInfo.id}/exercise-transactions"
    val (_, response, result) =
            Fuel.post(path)
                    .appendHeader("Accept", "application/json;charset=UTF-8")
                    .appendHeader("Authorization", "Basic ${userInfo.token}")
                    .responseObject<TransactionLocation>()
    println(response)
    return result
}

fun listExercises(userInfo: UserInfo): Result<Exercises, FuelError> {
    val (_, response, result) =
            Fuel.get("https://www.polaraccesslink.com/v3/exercises")
                    .appendHeader("Accept", "application/json;charset=UTF-8")
                    .appendHeader("Authorization", "Basic ${userInfo.token}")
                    .responseObject<Exercises>()
    println(response)
    return result
}