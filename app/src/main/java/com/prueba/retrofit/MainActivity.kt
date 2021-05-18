package com.prueba.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.prueba.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private val articleList = mutableListOf<Articles>()

    private var place: String ="us"
    private var type: String = "business"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)

        intRecyclerView()
        searchNew(type,place)

        binding.btnUs.setOnClickListener{
            place = "us"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnCo.setOnClickListener{
            place = "co"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnRu.setOnClickListener {
            place = "ru"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnCa.setOnClickListener {
            place = "ca"
            searchNew(type,place)
            showMessage("error")
        }

        binding.btnBusiness.setOnClickListener{
            type = "business"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnHealth.setOnClickListener{
            type = "health"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnScience.setOnClickListener {
            type = "science"
            searchNew(type,place)
            showMessage("error")
        }
        binding.btnSports.setOnClickListener {
            type = "sports"
            searchNew(type,place)
            showMessage("error")
        }

    }

    private fun intRecyclerView(){
        adapter = ArticleAdapter(articleList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }

    private fun searchNew(category:String, country:String){
        val api = Retrofit2()

        CoroutineScope(Dispatchers.IO).launch {
            val call = api.getService()?.getNewsByCategory(country,category,"3cc67f2383ed433ba1646971765a2fbb")
            val news: NewsResponse? = call?.body()

            runOnUiThread{
                if(call!!.isSuccessful){
                    if(news?.status.equals("ok")){
                        val article = news?.articles ?: emptyList()
                        articleList.clear()
                        articleList.addAll(article)
                        adapter.notifyDataSetChanged()
                    }else{
                        showMessage("Error en webservice")
                    }

                }else{
                    showMessage("Error en retrofit")
                }
            }
        }
    }
    private fun showMessage(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchNew(query.toLowerCase(Locale.ROOT), query.toLowerCase(Locale.ROOT))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}