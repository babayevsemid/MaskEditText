package com.semid.maskedittext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MaskTextInputEditText : TextInputEditText {
    private val maskUtil = MaskUtil()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaskTextInputEditText)
        val prefix = a.getString(R.styleable.MaskTextInputEditText_prefix) ?: ""
        val mask = a.getString(R.styleable.MaskTextInputEditText_mask) ?: ""
        val staticPrefix = a.getBoolean(R.styleable.MaskTextInputEditText_staticPrefix, true)
        val hideKeyboardWhenMaskComplete =
            a.getBoolean(R.styleable.MaskTextInputEditText_hideKeyboardWhenComplete, true)

        setMask(prefix, mask, staticPrefix)
        hideKeyboardWhenMaskComplete(hideKeyboardWhenMaskComplete)
        a.recycle()

        setup()
    }

    fun setMask(prefix: String?, mask: String?, staticPrefix: Boolean) {
        maskUtil.config(prefix.toString(), mask.toString(), staticPrefix)
    }

    fun hideKeyboardWhenMaskComplete(hideKeyboard: Boolean) {
        maskUtil.hideKeyboardWhenMaskComplete(hideKeyboard)
    }

    fun setup() {
        maskUtil.setup(this)
    }
}