package com.task.aplikasiandroidsederhana


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.recyclerview.ListHeroAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Anime>()




    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)


        if (savedInstanceState == null) {
            list.addAll(getListHeroes())
            showRecyclerList()
        }
        else {
            val stateList = savedInstanceState.getParcelableArrayList<Anime>("list")
            if (stateList != null) {
                list.addAll(stateList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun getListHeroes(): ArrayList<Anime> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataSortDesc = resources.getStringArray(R.array.data_sortdesc)
        val dataPersonalDescription = resources.getStringArray(R.array.personal_description)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

        val listSize = minOf(
            dataName.size,
            dataSortDesc.size,
            dataPersonalDescription.size,
            dataDescription.size,
            dataPhoto.length()
        )
        val listHero = ArrayList<Anime>()
//        for (i in dataName.indices) {
        for (i in 0 until listSize) {
            val isianime = Anime(dataName[i],dataSortDesc[i],dataPersonalDescription[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(isianime)
        }
        dataPhoto.recycle()
        return listHero
    }

    private fun showRecyclerList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Anime) {

                val intentToDetail = Intent(this@MainActivity, DetailHero::class.java)
                intentToDetail.putExtra(DetailHero.Heros, data)
                startActivity(intentToDetail)
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



}