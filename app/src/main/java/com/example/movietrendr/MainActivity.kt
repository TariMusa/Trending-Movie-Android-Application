package com.example.movietrendr

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    var radioGrp: RadioGroup? = null
    lateinit var radioButton1: RadioButton
    lateinit var radioButton2: RadioButton
    var period: String = "week"

    private lateinit var btnSearch : Button
    var movieAttributes = listOf<String>("poster_path", "title","vote_average","release_date")
    private lateinit var rvMovies: RecyclerView
    var attributesUrlList = mutableListOf<String>()
    var attributesTitleList = mutableListOf<String>()
    var attributesRatingList = mutableListOf<String>()
    var attributesDateList = mutableListOf<String>()
    var imageURL = "https://www.themoviedb.org/t/p/w1280"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGrp = findViewById(R.id.radioGrp)
        radioButton1 = findViewById(R.id.radio1)
        radioButton2 = findViewById(R.id.radio2)





        rvMovies = findViewById(R.id.rvSongList)
        getAttributesList(movieAttributes)


    }
    fun zeroAttributes(){
        attributesDateList.clear()
        attributesRatingList.clear()
        attributesTitleList.clear()
        attributesUrlList.clear()
    }

    fun onRadioButtonClicked(view: View){

        var wallText = findViewById<TextView>(R.id.etCurrent)
        if (view is RadioButton){
            val checked = view.isChecked

            if (view.getId() == R.id.radio1){
                Log.d("radio","Radio one is live")
                period = "week"
                wallText.setText(resources.getString(R.string.windowWeek,"this week"))
                wallText.setTextColor(Color.parseColor("#8273E8"))
                radioButton1.buttonTintList= ColorStateList.valueOf(getColor(R.color.light_gray))
            }else{
                Log.d("radio","Radio two is live")
                period = "day"
                wallText.setText(resources.getString(R.string.windowDay,"today"))
                wallText.setTextColor(Color.parseColor("#E81614"))
                radioButton2.buttonTintList= ColorStateList.valueOf(getColor(R.color.red))
            }
            zeroAttributes()
            getAttributesList(movieAttributes)

        }



    }

    private fun getAttributesList( movieAttributes: List<String>) {
        val client = AsyncHttpClient()
        var listIndex = 0
        client["https://api.themoviedb.org/3/trending/movie/$period?api_key=4a07465c8340445a1708b80c76c16b8f", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int,headers: Headers,json: JsonHttpResponseHandler.JSON) {
                var arrayLength = json.jsonObject.getJSONArray("results").length()
                //get list with the four attributes and add to main list
                for (i in 0 until arrayLength ) {
                    for (j in movieAttributes) {
                        if (j == "poster_path"){
                            var currentAttribute = imageURL + (json.jsonObject.getJSONArray("results")
                            .getJSONObject(i).getString(j))
                            Log.d("JSON results", "$currentAttribute")
                            attributesUrlList.add(currentAttribute)

                        }

                        else if(j == "vote_average"){
                            var currentAttribute = "Rating: " + (json.jsonObject.getJSONArray("results")
                            .getJSONObject(i).getString(j).substringBefore(".")) + "/10"
                            Log.d("JSON results", "$currentAttribute")
                            attributesRatingList.add(currentAttribute)

                        }
                        else  if(j == "title")
                        {var currentAttribute = (json.jsonObject.getJSONArray("results")
                            .getJSONObject(i).getString(j))
                            Log.d("JSON results", "$currentAttribute")
                            attributesTitleList.add(currentAttribute)}

                        else{
                            var currentAttribute = (json.jsonObject.getJSONArray("results").getJSONObject(i).getString(j))
                                Log.d("JSON results", "$currentAttribute")
                                attributesDateList.add(currentAttribute)
                        }

                    }

                }


                val adapter = MovieAdapter(attributesUrlList, attributesTitleList, attributesRatingList, attributesDateList)
                rvMovies.adapter = adapter
                rvMovies.layoutManager = LinearLayoutManager(this@MainActivity)
                rvMovies.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            }

            override fun onFailure(statusCode: Int,headers: Headers?,errorResponse: String,throwable: Throwable?) {
                Log.d("Pic error", errorResponse)
            }

        }]
    }

}