package com.example.kapriz.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foryourself.data.types.Image
import com.parse.ParseFile


fun Fragment.toast(message: String){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}
fun ParseFile.toImage(): Image =
    Image(name = name, type = "File", url = url)


//fun EditText.validateEmail(): Boolean {
//    val email = this.text.toString()
//    return email.contains("@") && email.contains(".") && email.length > 7
//}
//fun Activity.intentClearTask(activity: Activity) {
//    val intent = Intent(this, activity::class.java)
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    startActivity(intent)
//}
//fun Fragment.intentClearTask(activity: Activity) {
//    val intent = Intent(requireActivity(), activity::class.java)
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    startActivity(intent)
//}
//fun EditText.validatePassword(): Boolean {
//    val password = this.text.toString()
//    return password.length >= 8
//}
//fun EditText.validateName(): Boolean {
//    val name = this.text.toString()
//    return name.length >= 2
//}
//fun EditText.validateLastName(): Boolean {
//    val lastName = this.text.toString()
//    return lastName.length >= 2
//}
//fun EditText.validateEditPhone(): Boolean {
//    val phone = this.text.toString()
//    return phone.length == 13
//}