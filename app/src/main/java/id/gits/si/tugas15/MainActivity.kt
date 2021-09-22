package id.gits.si.tugas15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.tugas15.model.MovieResponse
import id.gits.si.tugas15.model.ResultsItem
import id.gits.si.tugas15.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    val movieApiAdapter = MovieAPIAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovie.layoutManager = LinearLayoutManager(this)
        rvMovie.adapter = movieApiAdapter
        getDataMovie()
    }

    private fun getDataMovie() {
        RetrofitClient.instance.getMovie()
            .enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>?,
                    response: Response<MovieResponse>?
                ) {
                    if (response!!.isSuccessful){
                        tampilMovie(response.body())
                        val result = response.body().results
                        for (item in result!!){
                            detailMovie(item!!.id)
                        }
                    }else{
                        Toast.makeText(this@MainActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }
    private fun tampilMovie(data : MovieResponse){
        val result = data.results
        movieApiAdapter.setData(result as List<ResultsItem>)
    }

    private fun detailMovie(id: Int?){
        RetrofitClient
    }
}
