package com.semid.maskedittext

import android.app.Activity
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MaskUtil {
    private var hideKeyboardWhenMaskComplete = true
    private var static = true
    private var prefix = ""
    private var mask = ""
    private var startWith = ArrayList<String>()
    private var editText: EditText? = null

    fun config(prefix: String, mask: String, static: Boolean) {
        this.prefix = prefix;
        this.static = static;

        this.mask = "$prefix$mask"
    }

    fun startWith(startWith: ArrayList<String>) {
        this.startWith = startWith
    }

    fun hideKeyboardWhenMaskComplete(boolean: Boolean) {
        hideKeyboardWhenMaskComplete = boolean
    }

    fun setup(textInputEditText: TextInputEditText) {
        start(textInputEditText)
    }

    fun setup(editText: EditText) {
        start(editText)
    }

    private fun start(editText: EditText){
        this.editText = editText
        editText.setText(prefix)
        editText.filters = arrayOf(MaskFilter())

        val spaceIndexList: ArrayList<Int> = ArrayList()

        for (item in mask.indices) {
            if (mask[item].toString() != "#" && !mask[item].isDigit()) {
                spaceIndexList.add(item)
            }
        }


        editText.addTextChangedListener(object : TextWatcher {

            var length_before = 0;
            var editIndex = 0;

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                length_before = s.length
                editIndex = start
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (length_before < s.length) {
                    if (!spaceIndexList.contains(s.length - 1) &&
                        (s[s.length - 1] == ' ' ||
                                s[s.length - 1] == '+')
                    ) {
                        s.replace(s.length - 1, s.length, "")
                    }

                    if (spaceIndexList.contains(s.length)) {
                        s.append(mask[s.length].toString())
                    }

                    val current = s.toString()

                    for (i in current.indices) {
                        if (mask[i].toString() != "#" && current[i] != mask[i]) {
                            if (s.length == mask.length) {
                                if (mask[i].toString() != "#" && current[i].isDigit()) {
                                    s.delete(i + 1, i + 2)
                                    s.insert(i - 1, mask[i].toString())
                                }
                                return
                            } else if (!current[i].isDigit()) {
                                s.delete(i, i + 1)
                                return
                            }

                            if (s.length != mask.length) {
                                s.insert(i, mask[i].toString())
                                break
                            }
                        } else if (mask[i].toString() == "#" && !current[i].isDigit()) {
                            s.delete(i, i + 1)
                        }
                    }
                }

                if (s.length == mask.length && hideKeyboardWhenMaskComplete) {
                    editText.hideKeyboard()
                }
            }
        })
    }

    inner class MaskFilter : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {

            if (static) {
                if (!dest.subSequence(0, dstart).startsWith(prefix)) {
                    return if (dstart < dend) {
                        prefix.subSequence(dstart, dend)
                    } else {
                        editText?.apply {
                            setSelection(text.length)
                        }

                        ""
                    }
                }
            }

            val inputString = StringBuilder().append(dest).insert(dstart, source).toString()

            var enableEdit = false;

            for (item in startWith) {
                enableEdit = if (enableEdit)
                    break
                else if (item.length >= dstart + 1) {
                    item.startsWith(inputString.substring(0, dstart + 1))
                } else
                    true
            }

            if (startWith.isNullOrEmpty())
                enableEdit = true

            if (!enableEdit)
                return ""
            else if (inputString.length != mask.length + 1)
                return null

            return ""
        }
    }

    fun View.hideKeyboard() {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}