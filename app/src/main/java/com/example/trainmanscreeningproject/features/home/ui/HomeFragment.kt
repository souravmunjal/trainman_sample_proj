package com.example.trainmanscreeningproject.features.home.ui

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import bind
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.trainmanscreeningproject.R
import com.example.trainmanscreeningproject.core.application.TrainmanApplication
import com.example.trainmanscreeningproject.features.home.dagger.DaggerHomeDataComponent
import com.example.trainmanscreeningproject.features.home.data.entities.Data
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import com.example.trainmanscreeningproject.features.home.data.entities.GifData
import com.example.trainmanscreeningproject.features.home.data.source.HomeRepository
import com.example.trainmanscreeningproject.features.home.data.view_models.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_gif.view.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject


class HomeFragment : Fragment() {
    @Inject
    lateinit var home: HomeRepository

    @Inject
    lateinit var viewModel : HomeViewModel

    lateinit var gifData:GifData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        DaggerHomeDataComponent.builder()
            .coreDataComponent(TrainmanApplication.coreDataComponent(requireActivity().baseContext))
            .activityContext(requireActivity().baseContext)
            .build()
            .inject(this)


        if(isNetworkAvailable()){
            //fetch gifs from remote
            showAlertDialogBox()

            viewModel.getGifs().observe(viewLifecycleOwner, Observer {
                view.recycler_view.bind(it.data!!)
                    .map(R.layout.item_gif, predicate = { it is Data }) { data, _ ->
                        Glide.with(this)
                            .load(data.images!!.downsized!!.url)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(this.gif)
                        this.gif.setOnClickListener{
                            var bundle = Bundle()
                            bundle.putParcelable("image_url",data)
                            findNavController().navigate(R.id.action_homeFragment_to_gifDetailsFragment,bundle)
                        }

                    }.layoutManager(GridLayoutManager(context,2))
                gifData=it
            })
        }else{
            viewModel.getLocalGifs().observe(viewLifecycleOwner, Observer {
                if(it.isEmpty() || it.size==0){
                    Toast.makeText(requireActivity(),"No Data to Show!!", Toast.LENGTH_LONG)
                }else {
                    view.recycler_view.bind(it)
                        .map(R.layout.item_gif, predicate = { it is Gif }) { data, _ ->
                            try {
                                val file = File(
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                    "/" + data.title + ".gif"
                                )
                                val drawable = GifDrawable(file)
                                this.gif.background = drawable
                                this.gif.setOnClickListener{
                                    var bundle = Bundle()
                                    bundle.putParcelable("image_url",data)
                                    findNavController().navigate(R.id.action_homeFragment_to_gifDetailsFragment,bundle)
                                }
                            } catch (e: Exception) {
                                Log.e("TAG", "onCreateView: " + e.message)
                            }
                        }.layoutManager(GridLayoutManager(context, 2))
                }
            })
        }
        return view
    }

    private fun isNetworkAvailable(): Boolean {
        val cm =
            requireContext()!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
                return true;
            }
        } else {
            // not connected to the internet
            return false
        }
        return false
    }

    private fun showAlertDialogBox(){
        AlertDialog.Builder(context)
            .setTitle("Save Gifs Offline")
            .setMessage("Do You want to save gifs online")
            .setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    viewModel.getGifs().observe(viewLifecycleOwner, Observer {
                        for (data in gifData.data!!){
                            val downloadmanager =
                                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                            val uri: Uri = Uri.parse(data.images!!.downsized!!.url)
                            viewModel.addGifinDb(Gif(0,data.title))
                            val request = DownloadManager.Request(uri)
                            request.setTitle(data.title)
                            request.setDescription("Downloading")
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            request.setVisibleInDownloadsUi(false)
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,data.title+".gif")
                            downloadmanager!!.enqueue(request)
                        }
                    })

                }
            })
            .setNegativeButton(android.R.string.no, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            .show()
    }


}