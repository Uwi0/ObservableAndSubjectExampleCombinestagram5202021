package com.kakapo.observableandsubjectexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class SharedViewModel : ViewModel() {

    //1. composite disposable untuk subscription
    private val disposables = CompositeDisposable()
    //2. image subject akan memancarkan nilai MutableList<Photo>
    private val imagesSubject: BehaviorSubject<MutableList<Photo>> =
        BehaviorSubject.createDefault(mutableListOf())
    //3. Terakhir, menggunakan variabel selected photo, yang merupakan live data
    //   object, untuk stream list ke mainActivity
    private val selectedPhotos = MutableLiveData<List<Photo>>()

    init{
        imagesSubject.subscribe{ photos ->
            selectedPhotos.value = photos
        }.addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun getSelectedPhotos(): LiveData<List<Photo>> {
        return selectedPhotos
    }


    fun clearPhotos(){
        imagesSubject.value?.clear()
        imagesSubject.onNext(imagesSubject.value!!)
    }

    fun subscribeSelectedPhotos(selectedPhotos: Observable<Photo>){
        selectedPhotos
            .doOnComplete{
                Log.v("Shad]red View Model", "Completed Selecting photo")
            }
            .subscribe{ photo ->
                imagesSubject.value?.add(photo)
                imagesSubject.onNext(imagesSubject.value!!)
            }
            .addTo(disposables)
    }

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