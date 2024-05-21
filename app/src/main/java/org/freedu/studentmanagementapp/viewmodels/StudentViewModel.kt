package org.freedu.studentmanagementapp.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.freedu.studentmanagementapp.models.Student
import org.freedu.studentmanagementapp.repositories.StudentRepository

class StudentViewModel(private val repository: StudentRepository):ViewModel() {
    private val _students = MutableLiveData<List<Student>>()
    val students:LiveData<List<Student>> get() = _students

    fun addStudent(student: Student, imageUri:Uri){
        repository.addStudent(student, imageUri){
            if(it){
                loadStudents()
            }
        }
    }

    fun loadStudents() {
        repository.getStudents { _students.value = it }
    }

    fun updateStudent(student: Student){
        repository.updateStudent(student){
            if(it){
                loadStudents()
            }
        }
    }
    fun deleteStudent(studentId:String){
        repository.deleteStudent(studentId){
            if(it){
                loadStudents()
            }
        }
    }
}