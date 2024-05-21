package org.freedu.studentmanagementapp.models

import android.os.Parcelable
import java.io.Serializable

data class Student(
    val profileImage:String ="",
    val fullName:String ="",
    val studentId:String = "",
    val subject:String = "",
    val address:String = "",
    val email:String = "",
    val phone:String = ""
):Serializable
