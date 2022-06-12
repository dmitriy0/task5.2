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


        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://akabab.github.io/superhero-api/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)


        val call: Call<ArrayList<Hero>>? = apiService.loadHero("all.json")
        call!!.enqueue(object : Callback<ArrayList<Hero>> {

            override fun onResponse( call: Call<ArrayList<Hero>>, response: Response<ArrayList<Hero>>) {
                this@MainActivity.runOnUiThread {

                    val adapter = Adapter(response.body()!!, this@MainActivity)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ArrayList<Hero>>, t: Throwable) {

            }
        })




    }


}