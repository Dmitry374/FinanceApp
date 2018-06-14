package com.example.financeapp.ui.records

//import android.support.v4.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.RecyclerRecordsAdapter
import com.example.financeapp.adapter.SpinnerBillsAllRecordsAdapter
import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.common.Constants.Companion.UPDATE_ROW
import com.example.financeapp.network.Model
import com.example.financeapp.ui.main.AddRecordActivity
import kotlinx.android.synthetic.main.fragment_records.*


class FragmentRecords : BasePerFragment() {

    lateinit var spinner: Spinner

    lateinit var listNameBills: ArrayList<String>  // Список счетов

    private lateinit var listRecords: ArrayList<Model.Record>  // Список записей

    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var nameSelectedBill: String  // Имя выбранного счета

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_records, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Hide Fab on scrolling
        nsvRecords.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                fabRecords.hide()
            } else {
                fabRecords.show()
            }
        })

        fabRecords.setOnClickListener {

            if (getBillCount() == 0){
                Toast.makeText(activity, getText(R.string.text_list_bills_empty), Toast.LENGTH_SHORT).show()
            } else if (nameSelectedBill == getText(R.string.text_my_bills) as String){  // Мои счета; выбираем первый счет, т.к. счета "Мои счета" нет
                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("nameSelectedBill", listNameBills[0])
                startActivity(intent)
            } else {
                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("nameSelectedBill", nameSelectedBill)
                startActivity(intent)
            }

        }

        fragmentRecordViewModel.setRemindSelectedBill(0)  // Запоминаем в SPref по умолчанию

        listRecords = ArrayList<Model.Record>()   // Инициализация списка

        spinner = activity.findViewById(R.id.spinnerRecordsBills)
        spinner.visibility = View.VISIBLE

        listNameBills = fragmentRecordViewModel.getListBillsName()

        listNameBills.add(getText(R.string.text_my_bills) as String)

        spinner.adapter = SpinnerBillsAllRecordsAdapter(activity, listNameBills)  // Адаптер

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {

                fragmentRecordViewModel.setRemindSelectedBill(position)

                nameSelectedBill = listNameBills[position]

                updateRecycler(listNameBills[position])

                showTextEmptyRecords(nameSelectedBill)
            }

        }

    }

    private fun updateRecycler(nameBill: String){

        listRecords.clear()

        nameSelectedBill = listNameBills[fragmentRecordViewModel.getRemindSelectedBill()]

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerRecord.layoutManager = linearLayoutManager

        recyclerRecord.isNestedScrollingEnabled = false

        if (nameBill == getText(R.string.text_my_bills) as String){
            listRecords = fragmentRecordViewModel.getAllRecordsInReverseOrder()

            setRecyclerAdapter(listRecords)

        } else {
            listRecords = fragmentRecordViewModel.getAllRecordsByBillReverseOrder(nameBill)

            setRecyclerAdapter(listRecords)
        }

    }

    private fun setRecyclerAdapter(listRecords: ArrayList<Model.Record>){

        recyclerRecord.adapter = RecyclerRecordsAdapter(activity, listRecords, object : RecyclerRecordsAdapter.OnItemClickListener {
            override fun onItemClick(item: Model.Record, position: Int) {

                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("type", UPDATE_ROW)
                intent.putExtra("id", listRecords[position].id)
                startActivity(intent)

            }
        })

    }

    private fun getBillCount(): Int{
        return fragmentRecordViewModel.getBillCount()
    }

    private fun showTextEmptyRecords(nameSelectedBill: String){
        if (nameSelectedBill == getText(R.string.text_my_bills) as String){
            if (fragmentRecordViewModel.getRecordsCount() == 0){
                no_records_text_fr_records.visibility = View.VISIBLE
                recyclerRecord.visibility = View.GONE
            } else {
                no_records_text_fr_records.visibility = View.GONE
                recyclerRecord.visibility = View.VISIBLE
            }

        } else {

            if (fragmentRecordViewModel.getRecordsCountByBill(nameSelectedBill) == 0){
                no_records_text_fr_records.visibility = View.VISIBLE
                recyclerRecord.visibility = View.GONE
            } else {
                no_records_text_fr_records.visibility = View.GONE
                recyclerRecord.visibility = View.VISIBLE
            }

        }

    }

    override fun onResume() {
        super.onResume()

        updateRecycler(listNameBills[fragmentRecordViewModel.getRemindSelectedBill()])

        showTextEmptyRecords(listNameBills[fragmentRecordViewModel.getRemindSelectedBill()])
    }
}