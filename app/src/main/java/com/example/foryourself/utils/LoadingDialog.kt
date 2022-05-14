package com.example.foryourself.utils

import android.app.ProgressDialog
import android.content.Context

class LoadingDialog (private val context: Context, private val message: String){

    private val dialog: ProgressDialog by lazy(LazyThreadSafetyMode.NONE) { dialog() }
    private fun dialog(): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(message)
        progressDialog.setCancelable(false)
        return progressDialog
    }
    fun show() = dialog.show()
    fun dismiss() = dialog.dismiss()
}

