package com.semid.maskedittext

import android.app.Activity
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter(value = ["android:text", "android:textChanged"], requireAll = false)
fun MaskEditText.setText(text: String?, listener: InverseBindingListener) {
    if (this.getUnMaskText() != text?.filter { it.isDigit() }.toString()) {
        this.setText(text)
    }

    doAfterTextChanged {
        listener.onChange()
    }
}

@InverseBindingAdapter(attribute = "android:text", event = "android:textChanged")
internal fun MaskEditText.getUnMaskText(): String {
    return this.text?.filter { it.isDigit() }.toString()
}

fun String.mask(mask: String) = SpannableStringBuilder("").also {s->
    val current = toString().replace(" ", "")
    var digits = current.filter { it.isDigit() }

    for (char in mask) {
        if (char == '#') {
            if (digits.isNotEmpty()) {
                s.insert(s.length, digits.first().toString())

                digits = digits.removePrefix(digits.first().toString())
            } else {
                break
            }
        } else {
            s.insert(s.length, char.toString())
        }
    }
}.toString()

internal fun EditText.setSelectionSafety(index: Int) {
    if (index >= 0 && index <= text.length)
        setSelection(index)
}

internal fun String.subStringSafety(startIndex: Int, endIndex: Int): String {
    if (startIndex < 0 || endIndex >= length || startIndex >= endIndex)
        return this

    return substring(startIndex, endIndex)
}

internal fun View.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}