package com.semid.maskedittext

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
    return this.text?.toString()?.unMask().orEmpty()
}

fun String.unMask(): String {
    return filter { it.isDigit() }
}

fun String.mask(mask: String): String {
    val current = replace(" ", "")
    val digits = current.filter { it.isDigit() }

    val maskedBuilder = StringBuilder()
    var digitIndex = 0

    for (char in mask) {
        if (char == '#') {
            if (digitIndex < digits.length) {
                maskedBuilder.append(digits[digitIndex])
                digitIndex++
            } else {
                break
            }
        } else {
            maskedBuilder.append(char)
        }
    }

    return maskedBuilder.toString()
}

internal fun Context?.copyToClipboard(text: String?) {
    (this?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.apply {
        val clip = ClipData.newPlainText("Copied", text)

        setPrimaryClip(clip)
    }
}

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