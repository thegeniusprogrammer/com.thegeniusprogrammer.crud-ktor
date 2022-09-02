package com.thegeniusprogrammer.dao

import com.thegeniusprogrammer.models.Student

interface StudentDao {

    suspend fun insert(name:String,age:Int):Student?

    suspend fun getAllStudents():List<Student>

    suspend fun getStudentById(studentId:Int):Student?

    suspend fun deleteStudent(studentId:Int):String

    suspend fun updateStudent(studentId:Int,name:String,age:Int):String?




}