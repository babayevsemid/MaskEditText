package com.semid.maskedittext

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

class MaskEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private var hideKeyboardWhenMaskComplete = true
    private var mask = ""
    private var maskDividerColor: Int = -1

    init {
        initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.MaskEditText).apply {
            mask = getString(R.styleable.MaskEditText_mask).orEmpty()
            maskDividerColor =
                getColor(R.styleable.MaskEditText_maskDividerColor, -1)
            hideKeyboardWhenMaskComplete =
                getBoolean(R.styleable.MaskEditText_hideKeyboardWhenComplete, false)
            recycle()
        }

        setup()
    }

    fun setMask(mask: String) {
        this.mask = mask

        text = text
    }

    fun setHideKeyboardWhenMaskComplete(hide: Boolean) {
        this.hideKeyboardWhenMaskComplete = hide
    }

    private fun setup() {
        val staticIndexList = arrayListOf<Int>()

        for (item in mask.indices) {
            if (mask[item].toString() != "#") {
                staticIndexList.add(item)
            }
        }

        post {
            if (mask.indexOfFirst { it == '#' } > 0) {
                setText(mask.substring(0, mask.indexOfFirst { it == '#' }))
            }
        }

        addTextChangedListener(object : TextWatcher {
            var lengthBefore = 0
            var editIndex = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                lengthBefore = s.length
                editIndex = start
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                removeTextChangedListener(this)

                val deleted = lengthBefore == s.length + 1

                val current = s.toString().replace(" ", "")
                var digits = current.filter { it.isDigit() }

                if (deleted && mask.getOrNull(editIndex) != '#') {
                    val index = mask.subStringSafety(0, editIndex).count { it == '#' }

                    if (editIndex > 1 && index > 0 && digits.isNotEmpty())
                        digits = digits.removeRange(index - 1, index)
                }

                s.delete(0, s.length)

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

                if (deleted) {
                    setSelectionSafety(
                        mask.subStringSafety(0, editIndex + 1).indexOfLast { it == '#' })
                } else {
                    setSelectionSafety(
                        mask.indexOf('#', editIndex + 1)
                    )
                }

                if (hideKeyboardWhenMaskComplete && s.length == mask.length) {
                    hideKeyboard()
                }

                checkMaskDividerColor(s)

                addTextChangedListener(this)
            }
        })
    }


    private fun checkMaskDividerColor(editable: Editable) {
        if (maskDividerColor == -1)
            return

        mask.forEachIndexed { index, c ->
            val canChangeColor = c != '#'

            if (canChangeColor && index + 1 <= editable.length) {
                editable.setSpan(
                    ForegroundColorSpan(maskDividerColor),
                    index,
                    index + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private fun EditText.setSelectionSafety(index: Int) {
        if (index >= 0 && index <= text.length)
            setSelection(index)
    }

    private fun String.subStringSafety(startIndex: Int, endIndex: Int): String {
        if (startIndex < 0 || endIndex >= length || startIndex >= endIndex)
            return this

        return substring(startIndex, endIndex)
    }

    fun View.hideKeyboard() {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}