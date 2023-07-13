package com.example.vladislav_test
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://cat-fact.herokuapp.com"

class MainActivity : AppCompatActivity() {

    private var scope: Job? = null

    private val adapter = CustomAdapter(object : OnItemClickListener {
        override fun onItemClick(cat: Cat) {
            Log.d("VLADOS", "сработало")
            val myDialogFragment = MyDialogFragment.newInstance(cat)
            myDialogFragment.show(supportFragmentManager, "myDialog")
        }
    })

    private val recyclerview
        get() = findViewById<RecyclerView>(R.id.recyclerview)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("VLADOS", "Программа запущена")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initRecyclerView()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        scope = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getCatFacts().execute()
                if (response.isSuccessful) {
                    val arrayList = ArrayList<Cat>()
                    response.body()?.let { cat ->
                        repeat(3) {
                            arrayList.add(
                                Cat(
                                    image = R.drawable.ic_baseline_folder_24,
                                    text = cat.text
                                )
                            )
                        }
                    }
                    withContext(Dispatchers.Main) {
                        adapter.addItems(arrayList)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("VLADOS", "не работает")
                }
            }
        }

    }

    private fun initRecyclerView() {
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope?.cancel()
    }

}
