package com.yaroslav.testzimad.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.yaroslav.testzimad.Constant
import com.yaroslav.testzimad.R
import com.yaroslav.testzimad.response.Data
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra(Constant.DETAIL_DATA)){
            val animal = intent.getParcelableExtra<Data>(Constant.DETAIL_DATA)
            val position = intent.getIntExtra(Constant.POSITION_ANIMAL, 0)

            if (animal != null) {
                Picasso.get()
                    .load(animal.url)
                    .into(imageView)

                tvTitle.text = animal.title
                tvPosition.text = position.toString()
            } else {
                finish()
            }
        } else {
            finish()
        }

    }

    override fun onBackPressed() {
        finish()
    }
}