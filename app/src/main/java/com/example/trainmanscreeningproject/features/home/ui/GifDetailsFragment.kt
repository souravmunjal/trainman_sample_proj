package com.example.trainmanscreeningproject.features.home.ui

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.trainmanscreeningproject.R
import com.example.trainmanscreeningproject.features.home.data.entities.Data
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import kotlinx.android.synthetic.main.fragment_gif_details.view.*
import kotlinx.android.synthetic.main.item_gif.view.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File

class GifDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_gif_details, container, false)
        val data = requireArguments().get("image_url")
        if(data is Data) {
            Glide.with(view)
                .load(data.images!!.downsized!!.url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(view.gif_full)

        }else if(data is Gif){
            try {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "/" + data.title + ".gif"
                )
                val drawable = GifDrawable(file)
                view.gif_full.background = drawable
            } catch (e: Exception) {
                Log.e("TAG", "onCreateView: " + e.message)
            }
        }
        return view;
    }


}