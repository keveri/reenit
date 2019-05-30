package reenit.accesslink

import reenit.accesslink.dataTypes.ApiCredentials
import reenit.accesslink.endpoint.TrainingData
import reenit.accesslink.endpoint.User

class Client(apiCredentials: ApiCredentials) {
    val user: User = User(apiCredentials)
    val trainingData: TrainingData = TrainingData(apiCredentials)
}
