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

        val btnUs: Button = findViewById(R.id.btnUs)
        val btnCo: Button = findViewById(R.id.btnCo)
        val btnRu: Button = findViewById(R.id.btnRu)
        val btnCa: Button = findViewById(R.id.btnCa)

        runSearch(type,place)

        btnUs.setOnClickListener{
            place = "us"
            runSearch(type,place)
        }
        btnCo.setOnClickListener{
            place = "co"
            runSearch(type,place)
        }
        btnRu.setOnClickListener {
            place = "ru"
            runSearch(type,place)
        }
        btnCa.setOnClickListener {
            place = "ca"
            runSearch(type,place)
        }

    }

    private fun runSearch(category:String, country:String){
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)

        intRecyclerView()
        searchNew(type,place)
    }

    private fun intRecyclerView(){
        adapter = ArticleAdapter(articleList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }

    private fun searchNew(category:String, country:String){
        val api = Retrofit2()

        CoroutineScope(Dispatchers.IO).launch {
            val call = api.getService()?.getNewsByCategory(country,category,"4b94054dbc6b4b3b9e50d8f62cde4f6c")
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