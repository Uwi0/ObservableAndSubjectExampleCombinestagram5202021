package com.kakapo.observableandsubjectexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakapo.observableandsubjectexample.databinding.LayoutPhotoBottomSheetBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class PhotosBottomDialogFragment : BottomSheetDialogFragment(), PhotosAdapter.PhotoListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mBinding: LayoutPhotoBottomSheetBinding
    private val selectedPhotosSubject = PublishSubject.create<Photo>()

    val selectedPhotos: Observable<Photo>
        get() = selectedPhotosSubject.hide()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = LayoutPhotoBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val ctx = activity
        ctx?.let {
            viewModel = ViewModelProvider(ctx).get(SharedViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.photosRecyclerView.layoutManager = GridLayoutManager(context, 3)
        mBinding.photosRecyclerView.adapter = PhotosAdapter(PhotoStore.photos, this)
    }

    override fun photoClicked(photo: Photo) {
        selectedPhotosSubject.onNext(photo)
    }

    companion object{
        fun newInstance(): PhotosBottomDialogFragment{
            return PhotosBottomDialogFragment()
        }
    }

}