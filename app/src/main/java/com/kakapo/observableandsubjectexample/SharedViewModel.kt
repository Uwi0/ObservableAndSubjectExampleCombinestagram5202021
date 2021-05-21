package com.kakapo.observableandsubjectexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class SharedViewModel : ViewModel() {

    fun saveBitmapFromImageView(imageView: ImageView, context: Context){
        val tmpImage = "${ System.currentTimeMillis() }.png"

        val output: OutputStream?

        val collagesDirectory = File(context.getExternalFilesDir(null), "collages")
        if(!collagesDirectory.exists()){
            collagesDirectory.mkdirs()
        }

        val file = File(collagesDirectory, tmpImage)

        try{
            output = FileOutputStream(file)
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            output.flush()
            output.close()
        }catch (e: IOException){
            Log.e("MainActivity", "Problem Saving Collage", e)
        }
    }
}