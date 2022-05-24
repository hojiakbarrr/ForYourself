package com.example.foryourself.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.getResponse.ImageFirst
import com.example.foryourself.data.retrofitResponse.getResponse.ImageMain
import com.example.foryourself.data.retrofitResponse.getResponse.ImageThird
import com.parse.ParseFile
import java.io.ByteArrayOutputStream


fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ParseFile.toImageFirst(): ImageFirst =
    ImageFirst(name = name, __type = "File", url = url)

fun ParseFile.toImageMain(): ImageMain =
    ImageMain(name = name, __type = "File", url = url)

fun ParseFile.toImageThird(): ImageThird =
    ImageThird(name = name, __type = "File", url = url)

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
fun Fragment.getImage(code:Int) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, code)
}

fun Fragment.switchToFragment(idFragment: Int,fragment: Fragment) {
    val manager: FragmentManager = requireActivity().supportFragmentManager
    manager.beginTransaction().replace(id, fragment).addToBackStack(null).commit()
}

