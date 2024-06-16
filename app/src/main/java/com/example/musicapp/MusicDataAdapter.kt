package com.example.musicapp

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.MusicCardBinding
import com.squareup.picasso.Picasso

class MusicDataAdapter(private val context:Activity, private val datalist:List<Data>):
    RecyclerView.Adapter<MusicDataAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= MusicCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // populate the data into the view
        val currentData= datalist[position]
        val mediaPlayer= MediaPlayer.create(context, currentData.preview.toUri())

        animation(holder.itemView)
        holder.title.text = currentData.title
        Picasso.get().load(currentData.album.cover).into(holder.image)

        holder.playBtn.setOnClickListener{
            mediaPlayer.start()
        }

        holder.pauseBtn.setOnClickListener{
            mediaPlayer.stop()
        }
    }

    inner class MyViewHolder(binding:MusicCardBinding) :RecyclerView.ViewHolder(binding.root){
        // create view in case the layout manager fails to create view for the data
        var image:ImageView
        var title:TextView
        var playBtn:ImageButton
        var pauseBtn:ImageButton

        init{
            image=binding.ivMusicImage
            title=binding.tvMusicTitle
            playBtn=binding.ibMusicPlay
            pauseBtn=binding.ibMusicPlay
        }
    }

    private fun animation(view:View){
        val anim = AlphaAnimation(0.0F,1.0f)
        anim.duration=800
        view.startAnimation(anim)
    }
}