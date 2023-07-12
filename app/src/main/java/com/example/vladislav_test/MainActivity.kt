package com.example.vladislav_test
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://cat-fact.herokuapp.com"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("VLADOS", "Программа запущена")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()



        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val array =  ArrayList<String>()
        for(i in 1..40) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getCatFacts().execute()
                    if (response.isSuccessful) {

                        val data = response.body()!!
                        array.add(data.text)
                        Log.d("VLADOS", array[i])


                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("VLADOS", "не работает")
                    }
                }
            }
        }

        for (word in array) {
            Log.d("QWERTY", word)
            data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "item"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        recyclerview.addOnItemTouchListener(RecyclerItemClickListenr(this, recyclerview, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                Log.d("VLADOS", position.toString())
                Log.d("VLADOS", "сработало")
                val myDialogFragment = MyDialogFragment()
                val manager = supportFragmentManager
                myDialogFragment.show(manager, "myDialog")
            }
            override fun onItemLongClick(view: View?, position: Int) {
                TODO("do nothing")
            }

        }))

    }

}
