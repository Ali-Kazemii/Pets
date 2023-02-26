package com.artinsoft.pets.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.artinsoft.pets.R

fun Context.alertDialog(
    title :String = "",
    message: String = "",
    listener: ()->Unit={}
){
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(getString(R.string.ok)) { _, _ ->
          listener.invoke()
        }
        .show()
}