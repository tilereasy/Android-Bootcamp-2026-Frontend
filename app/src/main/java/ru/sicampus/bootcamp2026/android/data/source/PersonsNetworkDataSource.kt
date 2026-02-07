package ru.sicampus.bootcamp2026.android.data.source

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.sicampus.bootcamp2026.android.data.dto.PersonDto
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonsNetworkDataSource {
    suspend fun getPerson(id: Long): Result<PersonDto> = runCatching {
        Network.client.get("${Network.HOST}/api/person/$id") {
            addAuthHeader()
        }.body()
    }

    suspend fun getPersonByEmail(email: String): Result<PersonResponse> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.get("${Network.HOST}/api/person/email/$email") {
                addAuthHeader()
            }
            if (result.status == HttpStatusCode.OK) {
                result.body<PersonResponse>()
            } else {
                error("Person not found for email: $email")
            }
        }
    }
}