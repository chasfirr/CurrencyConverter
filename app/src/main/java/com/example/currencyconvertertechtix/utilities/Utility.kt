package com.example.currencyconvertertechtix.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object Utility {

    fun showAlert(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setNeutralButton("OK") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }
}