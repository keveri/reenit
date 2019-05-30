package reenit.accesslink

import kotlin.test.Test
import kotlin.test.assertNotNull

import reenit.accesslink.dataTypes.ApiCredentials

class ClientTest {
    @Test fun testClientProperties() {
        val testCreds = ApiCredentials(0, "test_token")
        val client = Client(testCreds)
        assertNotNull(client.user, "Client should have user instance")
        assertNotNull(client.trainingData, "Client should have trainingData instance")
    }
}
