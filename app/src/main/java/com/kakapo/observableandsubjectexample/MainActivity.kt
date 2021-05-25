package com.kakapo.observableandsubjectexample

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kakapo.observableandsubjectexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        title = resources.getString(R.string.collage)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        mBinding.addButton.setOnClickListener{
            actionAdd()
        }

        mBinding.clearButton.setOnClickListener{
            actionClear()
        }

        mBinding.saveButton.setOnClickListener{
            actionSave()
        }

        //1. mengamati live data selected photo, yang mana memancarkan list dari object photo
        viewModel.getSelectedPhotos().observe(this, { photos ->
            photos?.let{
                //2. selanjutnya, jika ada photo, mapping setiap photo object ke dalama bitmap
                // menggunakan bitmap factory
                if(photos.isNotEmpty()){
                    val bitmaps = photos.map{
                        BitmapFactory.decodeResource(resources, it.drawable)
                    }
                    //3. selanjutnya menggabungkan list dari bitmap menggunakan combine images
                    val newBitmap = combineImage(bitmaps)
                    //4. terkahir mengatur collage image view menggunakan combine bitmap
                    mBinding.collageImage.setImageDrawable(
                        BitmapDrawable(resources, newBitmap)
                    )
                }else{
                    mBinding.collageImage.setImageResource(android.R.color.transparent)
                }

                updateUi(photos)
            }
        })
    }

    private fun actionAdd(){
        val addPhotoBottomDialogFragment = PhotosBottomDialogFragment.newInstance()
        addPhotoBottomDialogFragment.show(supportFragmentManager, "PhotosBottomDialogFragment")
        viewModel.subscribeSelectedPhotos(
            addPhotoBottomDialogFragment.selectedPhotos
        )
    }

    private fun actionClear(){
        viewModel.clearPhotos()
    }

    private fun actionSave(){
        println("actionSave")
    }

    private fun updateUi(photos: List<Photo>){
        mBinding.saveButton.isEnabled =
            photos.isNotEmpty() && (photos.size % 2 == 0)
        mBinding.clearButton.isEnabled = photos.size < 6
        title = if (photos.isNotEmpty()){
            resources.getQuantityString(R.plurals.photos_format, photos.size, photos.size)
        }else{
            resources.getString(R.string.collage)
        }
    }
}