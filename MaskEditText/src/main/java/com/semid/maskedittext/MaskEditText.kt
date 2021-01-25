package com.semid.maskedittext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MaskEditText : AppCompatEditText {
    private val maskUtil = MaskUtil()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaskEditText)
        val prefix = a.getString(R.styleable.MaskEditText_prefix) ?: ""
        val mask = a.getString(R.styleable.MaskEditText_mask) ?: ""
        val staticPrefix = a.getBoolean(R.styleable.MaskEditText_staticPrefix, true)
        val hideKeyboardWhenMaskComplete = a.getBoolean(R.styleable.MaskEditText_hideKeyboardWhenComplete, true)

        setMask(prefix, mask, staticPrefix)
        hideKeyboardWhenMaskComplete(hideKeyboardWhenMaskComplete)
        setup()

        a.recycle()
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