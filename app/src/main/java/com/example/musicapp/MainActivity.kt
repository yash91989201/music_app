package com.example.musicapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    lateinit var musicAdapter:MusicDataAdapter
    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        val activityMainRoot=binding.root
        setContentView(activityMainRoot)

        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitQuery= retrofitBuilder.getData("eminem")

        retrofitQuery.enqueue(object : Callback<MusicData?> {
            override fun onResponse(
                call: Call<MusicData?>,
                response: Response<MusicData?>
            ) {
                val musicList= response.body()?.data!!

                musicAdapter= MusicDataAdapter(this@MainActivity , musicList)
                binding.rvMusicList.adapter=musicAdapter
                binding.rvMusicList.layoutManager=LinearLayoutManager(this@MainActivity)
                Log.d("on response","onresponse"+ response.body())
            }

            override fun onFailure(call: Call<MusicData?>, t: Throwable) {
                Log.d("TAG: onFaliure","faliure"+ t.message)
            }
        })
    }
}