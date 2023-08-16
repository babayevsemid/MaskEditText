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
    private var mask = ""
    private var editText: EditText? = null

    fun config(mask: String) {
        this.mask = mask
    }

    fun hideKeyboardWhenMaskComplete(boolean: Boolean) {
        hideKeyboardWhenMaskComplete = boolean
    }


}