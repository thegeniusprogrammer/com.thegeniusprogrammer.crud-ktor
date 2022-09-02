package com.thegeniusprogrammer.routes

import com.thegeniusprogrammer.Constants.STUDENT
import com.thegeniusprogrammer.repository.StudentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


@KtorExperimentalLocationsAPI
fun Route.student(db: StudentRepository) {

    post(STUDENT) {
        val parameter = call.receive<Parameters>()
        val name = parameter["name"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Name is required")
        val age = parameter["age"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Age is required")

        try {

            val student = db.insert(name, age.toInt())
            student?.userId?.let {
                call.respond(HttpStatusCode.OK, student)
            } ?: call.respond(HttpStatusCode.BadRequest, "Student not created")


        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unknown error")
        }

    }



    get(STUDENT) {
        try {
            val students = db.getAllStudents()
            students.let {
                call.respond(HttpStatusCode.OK, students)
            }

        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unknown error")

        }
    }


    delete(STUDENT) {
        val parameter = call.receive<Parameters>()
        val userId = parameter["userId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "UserId is required")
        try {
            val student = db.deleteStudent(userId.toInt())
            student.let {
                call.respond(HttpStatusCode.OK, student)
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unknown error")
        }

    }

    put(STUDENT) {
        val parameters = call.receive<Parameters>()
        val userId = parameters["userId"] ?: return@put call.respond(HttpStatusCode.BadRequest, "USerId Required")
        val name = parameters["name"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Name Required")
        val age = parameters["age"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Age Required")
        try {
            val student = db.updateStudent(userId.toInt(), name, age.toInt())
            student.let {
                call.respond(HttpStatusCode.OK, it)
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unknown error")

        }


    }


}






