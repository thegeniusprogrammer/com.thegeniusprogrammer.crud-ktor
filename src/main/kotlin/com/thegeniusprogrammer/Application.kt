package com.thegeniusprogrammer

import com.thegeniusprogrammer.repository.DatabaseFactory
import com.thegeniusprogrammer.repository.StudentRepository
import com.thegeniusprogrammer.routes.student
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        DatabaseFactory.init()

        val db = StudentRepository()


        routing {
            student(db)
        }

        routing {

        }





    }.start(wait = true)



}


