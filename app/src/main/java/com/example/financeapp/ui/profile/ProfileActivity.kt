package com.example.financeapp.ui.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.network.Model
import com.example.financeapp.ui.custom.CircleTransform
import com.example.financeapp.ui.navigation.NavigationActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import java.util.*

class ProfileActivity : GoogleApiClientBaseActivity() {

    private lateinit var user: Model.User

    lateinit var dateOfBirth: String
    lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarProfile)

//        Отключение автоматического открытия клавиатуры
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarProfile.setNavigationOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }


        user = profileActivityViewModel.getUserData()

        edNameProfile.setText(user.name)
        edSurnameProfile.setText(user.surname)
        edEmailProfile.setText(user.email)

        if (user.datebirth == EMPTY_STRING){
            btnSetDateOfBirthProfile.text = getText(R.string.text_empty)
        } else {
            btnSetDateOfBirthProfile.text = user.datebirth
        }

        dateOfBirth = btnSetDateOfBirthProfile.text.toString()

        imgUserProfile.setOnClickListener {
            Toast.makeText(this, sPrefHelper.getUserEmail(), Toast.LENGTH_SHORT).show()
        }

        if (user.photourl == EMPTY_STRING){

            // Loading profile image
            Glide.with(this).load(R.drawable.profile_img)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgUserProfile)

            btnSetDateOfBirthProfile.text = getText(R.string.text_empty)

        } else {

            // Loading profile image
            Glide.with(this).load(user.photourl)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgUserProfile)

            btnSetDateOfBirthProfile.text = user.datebirth

        }

        //        Массив пол
        val listGender = resources.getStringArray(R.array.select_gender_list)


        // адаптер
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGender)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGenderProfile.adapter = adapter

        spinnerGenderProfile.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                gender = listGender[position]

            }

        }

//        Spinner по умолчанию
        if (user.gender == EMPTY_STRING){
            spinnerGenderProfile.setSelection(0)
        } else {
            when(user.gender){
                getText(R.string.text_male) -> spinnerGenderProfile.setSelection(1)
                getText(R.string.text_female) -> spinnerGenderProfile.setSelection(2)
            }
        }


//        Выход из аккаунта
        btnExitProfile.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

            profileActivityViewModel.deleteAllData()

            profileActivityViewModel.logOut(mGoogleApiClient)
        }


//        Set Date Of Birth
        btnSetDateOfBirthProfile.setOnClickListener {
            getDateOfBirth()
        }

////        Change Password
//        btnChangePasswordProfile.setOnClickListener {
//            when {
//                edPasswordProfile.text.toString().trim().length < 6 -> tilPasswordProfile.error = getText(R.string.error_length_password)
//                edPasswordProfile.text.toString().trim() != edConfirmPasswordProfile.text.toString().trim() -> tilConfirmPasswordProfile.error = getText(R.string.error_password_must_coincide)
//                else -> {
//                    Toast.makeText(this, "All OK", Toast.LENGTH_SHORT).show()
//                    tilPasswordProfile.isErrorEnabled = false
//                    tilConfirmPasswordProfile.isErrorEnabled = false
//
//
//                }
//
//            }
//        }



    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ok, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.btnCreateNewBill -> {
                profileActivityViewModel.updateUserDate(edNameProfile.text.toString().trim(),
                        edSurnameProfile.text.toString().trim(), "", gender, dateOfBirth, 0)

                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDateOfBirth(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR) - 20
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            btnSetDateOfBirthProfile.text = "$dayOfMonth/${monthOfYear+1}/$year"
            dateOfBirth = "$dayOfMonth/${monthOfYear+1}/$year"
        }, year, month, day)
        dpd.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
