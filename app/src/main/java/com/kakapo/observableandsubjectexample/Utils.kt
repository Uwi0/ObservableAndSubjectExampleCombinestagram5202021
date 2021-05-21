package com.kakapo.observableandsubjectexample

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import kotlin.math.ceil
import kotlin.math.sqrt

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = false ): View{
    return LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)
}

fun combineImage(bitmaps: List<Bitmap>): Bitmap?{
    val cs: Bitmap?

    val count = bitmaps.size
    val gridSize = ceil(sqrt(count.toFloat()))
    var numRows = gridSize.toInt()
    val numCols = gridSize.toInt()

    if((gridSize * gridSize - count) >= gridSize){
        numRows -= 1
    }

    val bitmap0 = bitmaps[0]
    val width = numCols * bitmap0.width
    val height = numCols * bitmap0.height

    cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val comboImage = Canvas(cs)

    for(row in 0 until numRows){
        for(col in 0 until numCols){
            val index = row * numCols + 1
            if(index < count){
                val bitmap = bitmaps[row * numCols + col]
                val left = col * bitmap0.width
                val top = row * bitmap.height
                comboImage.drawBitmap(bitmap, left.toFloat(), top.toFloat(), null)
            }
        }
    }

    return cs
}