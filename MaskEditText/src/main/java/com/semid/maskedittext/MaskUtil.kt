package com.semid.maskedittext

import android.app.Activity
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class MaskUtil {
    private var hideKeyboardWhenMaskComplete = true
    private var static = true
    private var prefix = ""
    private var mask = ""
    private var startWith = ArrayList<String>()

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

    fun setup(editText: EditText) {
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

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                length_before = s.length
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (length_before < s.length) {
                    if (!spaceIndexList.contains(s.length - 1) &&
                        (s[s.length - 1] == ' ' ||
                                s[s.length - 1] == '+')
                    ) {
                        s.replace(s.length - 1, s.length, "0")
                    }

                    if (spaceIndexList.contains(s.length)) {

                        s.append(mask[s.length].toString())
                    }

                    if (s.length == mask.length && hideKeyboardWhenMaskComplete) {
                        editText.hideKeyboard()
                    }
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

            if (static && dstart < dend) {
                if (!dest.subSequence(0, dstart).startsWith(prefix)) {
                    return prefix.subSequence(dstart, dend)
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