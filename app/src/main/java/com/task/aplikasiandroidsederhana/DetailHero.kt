package com.task.aplikasiandroidsederhana

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailHero : AppCompatActivity() {

    companion object{
        const val Heros = "Animes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail_hero)

        val dataPhoto: ImageView = findViewById(R.id.img_item_photo)
        val dataName: TextView = findViewById(R.id.tv_item_name)
        val dataSortDesc: TextView = findViewById(R.id.tv_item_sortdesc)
        val dataPersonalDescription: TextView = findViewById(R.id.tv_item_personal_description)
        val dataDescription: TextView = findViewById(R.id.tv_item_description)


        val anime =intent.getParcelableExtra<Anime>(Heros)

        anime?.let {
            val tvName: TextView = findViewById(R.id.tv_item_name)
            dataPhoto.setImageResource(it.photo)
            val tvSortDesc: TextView = findViewById(R.id.tv_item_sortdesc)
            val tvPersonalDescription: TextView = findViewById(R.id.tv_item_personal_description)
            val tvDescription: TextView = findViewById(R.id.tv_item_description)

            tvName.text = it.name
            tvSortDesc.text = it.sortdesc
            tvPersonalDescription.text = it.personalDescription
            tvDescription.text = it.description

        val btnShare: Button = findViewById(R.id.btn_share)
        btnShare.setOnClickListener {
            anime?.let {
                shareAnime(it)
            }

            val anime = if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra<Anime>(Heros, Anime::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra<Anime>(Heros)
            }

            if (anime != null) {
                val text = "Name : ${anime.name.toString()}"
                dataName.text = text
                dataPhoto.setImageResource(anime.photo)
                dataSortDesc.text = anime.sortdesc
                dataPersonalDescription.text = anime.personalDescription
                dataDescription.text = anime.description
            }
        }
    }}
        private fun shareAnime(anime: Anime) {
            val index = resources.getStringArray(R.array.data_name).indexOf(anime.name)
            val animeShare = """
            Name : ${anime.name}
            Sort Description : ${resources.getStringArray(R.array.data_sortdesc)[index]}
            Personal Description : ${resources.getStringArray(R.array.personal_description)[index]}
            Description : ${resources.getStringArray(R.array.data_description)[index]}
        """.trimIndent()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Anime Detail")
                putExtra(Intent.EXTRA_TEXT, animeShare)
            }

            startActivity(Intent.createChooser(sendIntent, "Share via"))

    }
}
