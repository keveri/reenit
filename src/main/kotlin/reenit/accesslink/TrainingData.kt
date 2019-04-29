package reenit.accesslink

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.responseObject

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
        val upload_time: String,
        val polar_user: String,
        val device: String,
        val start_time: String,
        val duration: String,
        val calories: Int,
        val distance: Float,
        val heart_rate: HeartRate,
        val training_load: Float,
        val sport: String,
        val has_route: Boolean,
        val club_id: Int,
        val club_name: String,
        val detailed_sport_info: String
)

typealias Exercises = Array<Exercise>

fun listExercises(userInfo: UserInfo): Result<Exercises, FuelError> {
    val (_, response, result) =
            Fuel.get("https://www.polaraccesslink.com/v3/exercises")
                    .appendHeader("Accept", "application/json;charset=UTF-8")
                    .appendHeader("Authorization", "Basic ${userInfo.token}")
                    .responseObject<Exercises>()
    println(response)
    return result
}