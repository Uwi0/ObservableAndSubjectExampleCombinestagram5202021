package com.kakapo.observableandsubjectexample

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
    }

    private fun actionAdd(){
        println("actionAdd")
    }

    private fun actionClear(){
        println("actionClear")
    }

    private fun actionSave(){
        println("actionSave")
    }
}