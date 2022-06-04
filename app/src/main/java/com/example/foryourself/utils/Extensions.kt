package com.example.foryourself.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageFirst
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageMain
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageThird
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama1
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama2
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama3
import com.google.android.material.snackbar.Snackbar
import com.parse.Parse.getApplicationContext
import com.parse.ParseFile
import java.io.ByteArrayOutputStream


fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.snaketoast(message: String,context: View) {
    Snackbar.make(context,message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snaketoast(message: String,context: View) {
    Snackbar.make(context, message, Toast.LENGTH_SHORT).show()
}

fun ParseFile.toImageFirst(): ImageFirst =
    ImageFirst(name = name, __type = "File", url = url)

fun ParseFile.toImageMain(): ImageMain =
    ImageMain(name = name, __type = "File", url = url)

fun ParseFile.toImageThird(): ImageThird =
    ImageThird(name = name, __type = "File", url = url)


fun ParseFile.toImageReklama1(): Reklama1 =
    Reklama1(name = name, __type = "File", url = url)

fun ParseFile.toImageReklama2(): Reklama2 =
    Reklama2(name = name, __type = "File", url = url)

fun ParseFile.toImageReklama3(): Reklama3 =
    Reklama3(name = name, __type = "File", url = url)


fun Fragment.image(uri: Uri): ByteArray {
    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun Context.image(uri: Uri): ByteArray {
    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun Context.uploadImage(uri: String, imageView: ImageView) {
    Glide.with(this)
        .load(uri)
        .into(imageView)
}

fun Context.uploadImage2(uri: Int, imageView: ImageView) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
        .into(imageView)
}

fun Fragment.getImage(code: Int) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, code)
}

fun Fragment.switchToFragment(idFragment: Int, fragment: Fragment) {
    val manager: FragmentManager = requireActivity().supportFragmentManager
    manager.beginTransaction().replace(id, fragment).addToBackStack(null).commit()
}

fun Fragment.dialog() {
    val builder = AlertDialog.Builder(requireActivity())
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.dialog, null)

    val address: TextView = dialogLayout.findViewById(R.id.address)
    val road: TextView = dialogLayout.findViewById(R.id.addres22)

    with(builder) {
        setPositiveButton("Да") { dialog, which ->
            //            val urir = Uri.parse("geo:40.534185,72.795369")
            val uri = "google.navigation:q=" + 40.534185 + "," + 72.795369 + "&mode=d"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)

        }.setNegativeButton("Нет") { dialog, which ->
                dialog.dismiss()
        }.setView(dialogLayout).create().show()
    }
}

fun Fragment.dialogabout() {
    val builder = AlertDialog.Builder(requireActivity())
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.dialog_about, null)

    val rate: TextView = dialogLayout.findViewById(R.id.rate)
    val razrabat: TextView = dialogLayout.findViewById(R.id.razrabot)

    razrabat.setOnClickListener {
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf("hojiakbar_1997@mail.ru"))
        email.putExtra(Intent.EXTRA_SUBJECT, "Обращение пользователя")
        email.type = "message/rfc822"
        startActivity(Intent.createChooser(email, "Choose an Email client :"))
    }

    with(builder) {
        setPositiveButton("") { dialog, which ->


        }.setNegativeButton("") { dialog, which ->
            dialog.dismiss()
        }.setView(dialogLayout).create().show()
    }
}


fun Fragment.dialogerror(text: String) {
    val builder = AlertDialog.Builder(requireActivity())
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.dialog_message, null)

    val error: TextView = dialogLayout.findViewById(R.id.txt_error)
    error.setText(text)

    with(builder) {
        setPositiveButton("") { dialog, which ->


        }.setNegativeButton("") { dialog, which ->
            dialog.dismiss()
        }.setView(dialogLayout).create().show()
    }
}


fun Fragment.dialogwhatsapp() {

    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+996709888585"+"&text="+"Здравствуйте, хотела уточнить у вас по поводу одной одежды)"))
        intent.setPackage("com.whatsapp")
        startActivity(intent)
    } catch (e: Exception) {
        toast("Надо установить What's app")
    }
}

fun Fragment.dialogworktime() {

    val builder = AlertDialog.Builder(requireActivity())
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.dialog_worktime, null)




    with(builder) {
        setPositiveButton("") { dialog, which ->


        }.setNegativeButton("") { dialog, which ->
            dialog.dismiss()
        }.setView(dialogLayout).create().show()
    }

}


fun <T> List<T>.lastElements(): List<T> {
    val list = mutableListOf<T>()
    for ((counter, i) in (size - 1 downTo 0).withIndex()) {
        list.add(counter, this[i])
    }
    return list
}

fun Fragment.dialogwork() {
//    val context: Context = getApplicationContext()
//    val inflater = layoutInflater
//    val toastView: View = inflater.inflate(R.layout.toast, null)
//
//    val toast = Toast(context)
//    toastView.setView(toast)
//
//    // Set layout to toast
//
//    // Set layout to toast
//    toast.setGravity(
//        Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL,
//        0, 0
//    )
//    toast.duration = Toast.LENGTH_LONG
//    toast.show()

}

