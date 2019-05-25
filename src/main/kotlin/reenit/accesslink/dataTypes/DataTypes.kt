package reenit.accesslink.dataTypes

import com.google.gson.annotations.SerializedName

data class UserExtraInfoResponse(
        val value: String,
        val index: Int,
        val name: String
)

data class UserResponse(
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
        val field: Array<UserExtraInfoResponse>
)


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
