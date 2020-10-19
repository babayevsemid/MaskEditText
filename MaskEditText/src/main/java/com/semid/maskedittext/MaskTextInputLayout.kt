package com.semid.maskedittext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputLayout

class MaskTextInputLayout : TextInputLayout {
    private val maskUtil = MaskUtil()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaskTextInputLayout)
        val prefix = a.getString(R.styleable.MaskTextInputLayout_prefix)
        val mask = a.getString(R.styleable.MaskTextInputLayout_mask)
        val staticPrefix = a.getBoolean(R.styleable.MaskTextInputLayout_staticPrefix, true)
        val hideKeyboardWhenMaskComplete =
            a.getBoolean(R.styleable.MaskTextInputLayout_hideKeyboard, true)

        setMask(prefix, mask, staticPrefix)
        hideKeyboardWhenMaskComplete(hideKeyboardWhenMaskComplete)
        setup()

        a.recycle()
    }

    fun setMask(prefix: String?, mask: String?, staticPrefix: Boolean) {
        maskUtil.config(prefix.toString(), mask.toString(), staticPrefix)

        Log.e("prefix", "${prefix}")
        Log.e("mask", "${mask}")
    }

    fun hideKeyboardWhenMaskComplete(hideKeyboard: Boolean) {
        maskUtil.hideKeyboardWhenMaskComplete(hideKeyboard)
    }

    fun setup() {
        Handler(Looper.myLooper()!!).postDelayed({
            maskUtil.setup(this)
        }, 200)
    }
}