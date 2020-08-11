package ru.adbmb.adext_lib

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> AppCompatActivity.observe(data: LiveData<T>, eventCallBack: (T) -> Unit) {
    data.observe(this, Observer { event ->
        event?.let { eventCallBack(event) }
    })
}


fun <T> Fragment.observe(data: LiveData<T>, eventCallBack: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer { event ->
        event?.let { eventCallBack(event)  }
    })
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.hideKeyboard(){
    val context = this.context
    context.let {
        val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}


fun EditText.onTextChange(onAfterTextChanged: OnAfterTextChangedListener) {
    addTextChangedListener(object : TextWatcher {
        private var text = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            s?.length?.let {
                onAfterTextChanged.complete(((it == 0)))
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    })
}

interface OnAfterTextChangedListener {
    fun complete (isEmpty: Boolean)
}