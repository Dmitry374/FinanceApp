package com.example.financeapp.common

import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import android.text.Selection.getSelectionStart
import android.text.Editable
import java.text.ParseException


/**
 * Created by Dima on 01.06.2018.
 */
//class NumberTextWatcher(private var editText: EditText) : TextWatcher {
//
//    private var df: DecimalFormat? = null
//    private var dfnd: DecimalFormat? = null
//    private var hasFractionalPart: Boolean = false
//
//    init {
//        df = DecimalFormat("#,###.##");
//        df!!.isDecimalSeparatorAlwaysShown = true;
//        dfnd = DecimalFormat("#,###");
//        this.editText = editText;
//        hasFractionalPart = false;
//    }
//
//    override fun afterTextChanged(s: Editable) {
//        editText.removeTextChangedListener(this)
//
//        try {
//            val inilen: Int
//            val endlen: Int
//            inilen = editText.text.toString().length
//
//            val v = s.toString().replace(String.toString(df!!.getDecimalFormatSymbols().groupingSeparator), "")
//            val n = df!!.parse(v)
//            val cp = editText.getSelectionStart()
//            if (hasFractionalPart) {
//                editText.setText(df!!.format(n))
//            } else {
//                editText.setText(dfnd!!.format(n))
//            }
//            endlen = editText.getText().toString().length
//            val sel = cp + (endlen - inilen)
//            if (sel > 0 && sel <= editText.getText().toString().length) {
//                editText.setSelection(sel)
//            } else {
//                // place cursor at the end?
//                editText.setSelection(editText.getText().toString().length - 1)
//            }
//        } catch (nfe: NumberFormatException) {
//            // do nothing?
//        } catch (e: ParseException) {
//            // do nothing?
//        }
//
//        editText.addTextChangedListener(this)
//    }
//
//    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//        if (s.toString().contains(String.valueOf(df!!.getDecimalFormatSymbols().decimalSeparator))) {
//            hasFractionalPart = true
//        } else {
//            hasFractionalPart = false
//        }
//    }
//
//}