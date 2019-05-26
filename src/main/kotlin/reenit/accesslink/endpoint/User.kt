package reenit.accesslink.endpoint

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.responseObject

import reenit.accesslink.dataTypes.ApiCredentials
import reenit.accesslink.dataTypes.UserResponse

class User(val apiCredentials: ApiCredentials) {
    // 401: old token
    // 200: created
    fun register(): Result<UserResponse, FuelError> {
        val data = "{\"member-id\": \"${apiCredentials.id}\"}"
        val (_, _, result) =
                Fuel.post("https://www.polaraccesslink.com/v3/users")
                        .body(data)
                        .appendHeader("Content-Type", "application/json")
                        .appendHeader("Accept", "application/json")
                        .appendHeader("Authorization", "Basic ${apiCredentials.token}")
                        .responseObject<UserResponse>()
        return result
    }

    fun info(): Result<UserResponse, FuelError> {
        val (_ , _, result) =
                Fuel.get("https://www.polaraccesslink.com/v3/users/${apiCredentials.id}")
                        .appendHeader("Authorization", "Basic ${apiCredentials.token}")
                        .appendHeader("Accept", "application/json")
                        .responseObject<UserResponse>()
        return result
    }

    //204: deleted
    fun delete() {
        val (_, response, result) =
                Fuel.delete("https://www.polaraccesslink.com/v3/users/${apiCredentials.id}")
                        .appendHeader("Authorization", "Basic ${apiCredentials.token}")
                        .responseString()
    }
}

