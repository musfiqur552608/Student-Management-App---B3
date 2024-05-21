package org.freedu.studentmanagementapp.repositories

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.freedu.studentmanagementapp.Utils.Constants.STUDENTS
import org.freedu.studentmanagementapp.models.Student
import java.util.UUID

class StudentRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private val studentCollection = firestore.collection(STUDENTS)

    fun addStudent(student: Student, imageUri: Uri, onComplete: (Boolean) -> Unit) {
        val imageRef = storage.child("profileImages/${UUID.randomUUID()}.jpg")
        imageRef.downloadUrl.addOnSuccessListener {
            val studentWithImage = student.copy(profileImage = it.toString())
            studentCollection.add(studentWithImage).addOnSuccessListener { onComplete(true) }
                .addOnFailureListener {
                    onComplete(false)
                }
        }.addOnFailureListener {
            onComplete(false)
        }
    }

    fun getStudents(onComplete: (List<Student>) -> Unit) {
        studentCollection.get().addOnSuccessListener {
            val students = it.toObjects(Student::class.java)
            onComplete(students)
        }.addOnFailureListener { onComplete(emptyList()) }
    }

    fun updateStudent(student: Student, onComplete: (Boolean) -> Unit) {
        studentCollection.document(student.studentId).set(student)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteStudent(studentId: String, onComplete: (Boolean) -> Unit) {
        studentCollection.document(studentId).delete().addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}