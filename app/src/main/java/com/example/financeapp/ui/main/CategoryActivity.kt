package com.example.financeapp.ui.main

import android.content.Intent
import android.os.Bundle
import com.example.financeapp.R
import com.example.financeapp.adapter.ListCategoryAdapter
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.network.Model
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_category.*

class CategoryActivity : BaseActivity() {

    private lateinit var listCategory: ArrayList<Model.Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbarCategory)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarCategory.setNavigationOnClickListener {
            finish()
        }

        listCategory = ArrayList()

        listCategory.add(Model.Category(resources.getText(R.string.category_buy) as String, R.drawable.img1))
        listCategory.add(Model.Category(resources.getText(R.string.category_food_and_drink) as String, R.drawable.img2))
        listCategory.add(Model.Category(resources.getText(R.string.category_house) as String, R.drawable.img3))
        listCategory.add(Model.Category(resources.getText(R.string.category_transport) as String, R.drawable.img4))
        listCategory.add(Model.Category(resources.getText(R.string.category_vehicle) as String, R.drawable.img5))
        listCategory.add(Model.Category(resources.getText(R.string.category_games) as String, R.drawable.img6))
        listCategory.add(Model.Category(resources.getText(R.string.category_life) as String, R.drawable.img7))
        listCategory.add(Model.Category(resources.getText(R.string.category_internet) as String, R.drawable.img8))
        listCategory.add(Model.Category(resources.getText(R.string.category_phone) as String, R.drawable.img9))
        listCategory.add(Model.Category(resources.getText(R.string.category_communication) as String, R.drawable.img10))
        listCategory.add(Model.Category(resources.getText(R.string.category_finance_expenses) as String, R.drawable.img11))
        listCategory.add(Model.Category(resources.getText(R.string.category_investments) as String, R.drawable.img12))
        listCategory.add(Model.Category(resources.getText(R.string.category_income) as String, R.drawable.img13))
        listCategory.add(Model.Category(resources.getText(R.string.category_other) as String, R.drawable.img14))

        val listAdapter = ListCategoryAdapter(this, listCategory)

        lvCategory.adapter = listAdapter

        lvCategory.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent()
            intent.putExtra("nameSelectedCategory", listCategory[position].name)
            intent.putExtra("iconSelectedCategory", listCategory[position].icon)
            setResult(RESULT_OK, intent)
            finish()
        }


    }

}
