package com.thegeniusprogrammer.repository

import com.thegeniusprogrammer.dao.StudentDao
import com.thegeniusprogrammer.models.Student
import com.thegeniusprogrammer.models.StudentTable
import com.thegeniusprogrammer.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class StudentRepository:StudentDao {


    override suspend fun insert(name: String, age: Int): Student? {
       var statement:InsertStatement<Number>? = null
        dbQuery {
            statement = StudentTable.insert { student->
                student[StudentTable.name] = name
                student[StudentTable.age] = age

            }
        }

        return rowToStudent(statement?.resultedValues?.get(0))

    }

    override suspend fun getAllStudents(): List<Student> {
      return dbQuery {
            StudentTable.selectAll().mapNotNull { rowToStudent(it) }
      }
    }

    override suspend fun getStudentById(studentId: Int): Student? {
       return dbQuery {
            StudentTable.select { StudentTable.userId.eq(studentId) }.mapNotNull { rowToStudent(it) }.singleOrNull()
       }
    }

    override suspend fun deleteStudent(studentId: Int): String {
        dbQuery {
            StudentTable.deleteWhere { StudentTable.userId.eq(studentId) }
       }
        return "Student deleted"
    }

    override suspend fun updateStudent(studentId: Int, name: String, age: Int): String {
       dbQuery {
            StudentTable.update({ StudentTable.userId.eq(studentId) }) { student ->
                student[StudentTable.name] = name
                student[StudentTable.age] = age
            }
       }
         return "Student updated successfully"
    }


    private fun rowToStudent(row: ResultRow?): Student ?{
        return if (row != null) {
            Student(
                userId = row[StudentTable.userId],
                name = row[StudentTable.name],
                age = row[StudentTable.age]
            )
        }else {
            null
        }

    }


}