package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var data: ArrayList<Hero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = ArrayList<Hero>()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = Adapter(data, this)
        recyclerView.adapter = adapter

        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/3202191630032155/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)

        for (i in 1..731) {
            val call: Call<Hero?>? = apiService.loadHero("$i")
            call!!.enqueue(object : Callback<Hero?> {
                override fun onResponse(call: Call<Hero?>, response: Response<Hero?>) {
                    this@MainActivity.runOnUiThread {
                        data.add(response.body()!!)
                        adapter.notifyItemInserted(data.size-1)
                    }
                }

                override fun onFailure(call: Call<Hero?>, t: Throwable) {
                }

            })
        }



    }


}