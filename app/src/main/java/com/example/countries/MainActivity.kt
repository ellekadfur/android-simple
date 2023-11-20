package com.example.countries

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.adapter.CountryAdapter
import com.example.countries.service.CountryApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: CountryApiService = retrofit.create(CountryApiService::class.java)
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var clData: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        // Set up RecyclerView
        countryAdapter = CountryAdapter(ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = countryAdapter

        // Call the fetchData method when the activity is created
        fetchData()
    }

    private fun init(){
        recyclerView = findViewById(R.id.recyclerView)
        clData = findViewById(R.id.cl_data)
        progressBar = findViewById(R.id.progress)
        tvNoData = findViewById(R.id.tv_nodata)
    }
    private fun fetchData() {
        progressBar.visibility = View.VISIBLE
        clData.visibility = View.GONE
        tvNoData.visibility = View.GONE
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                clData.visibility = View.GONE
                val getCountries = apiService.getCountries()
                countryAdapter.updateList(getCountries)
                countryAdapter.notifyDataSetChanged()

                if(getCountries.isNotEmpty()){
                    progressBar.visibility = View.GONE
                    clData.visibility = View.VISIBLE
                }else if(getCountries.isEmpty()){
                    //When Result is Empty
                    progressBar.visibility = View.GONE
                    clData.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                    tvNoData.text = "No Data Found"
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                clData.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
                tvNoData.text = "Something went Wrong"
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}