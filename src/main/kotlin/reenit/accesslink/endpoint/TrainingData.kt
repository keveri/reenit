package reenit.accesslink.endpoint

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.responseObject

import reenit.accesslink.dataTypes.Exercises
import reenit.accesslink.dataTypes.TransactionLocation
import reenit.accesslink.dataTypes.ApiCredentials

class TrainingData(val apiCredentials: ApiCredentials) {
    fun createTransaction(): Result<TransactionLocation, FuelError> {
        val path = "https://www.polaraccesslink.com/v3/users/${apiCredentials.id}/exercise-transactions"
        val (_, response, result) =
                Fuel.post(path)
                        .appendHeader("Accept", "application/json;charset=UTF-8")
                        .appendHeader("Authorization", "Basic ${apiCredentials.token}")
                        .responseObject<TransactionLocation>()
        println(response)
        return result
    }

    fun listExercises(): Result<Exercises, FuelError> {
        val (_, response, result) =
                Fuel.get("https://www.polaraccesslink.com/v3/exercises")
                        .appendHeader("Accept", "application/json;charset=UTF-8")
                        .appendHeader("Authorization", "Basic ${apiCredentials.token}")
                        .responseObject<Exercises>()
        println(response)
        return result
    }
}
