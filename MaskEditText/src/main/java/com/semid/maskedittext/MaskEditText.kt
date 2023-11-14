package com.semid.maskedittext

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import kotlin.math.abs

class MaskEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private var onTextChanged: (Editable) -> Unit = {}

    private var hideKeyboardWhenMaskComplete = true
    private var mask = ""
    private var maskDividerColor: Int = -1

    init {
        initAttr(attrs)

        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.removeItem(android.R.id.shareText)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    menu?.removeItem(android.R.id.textAssist)
                }
                return true
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = true

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId) {
                    android.R.id.copy -> {
                        val selectedText =
                            text.toString().subStringSafety(selectionStart, selectionEnd)

                        context.copyToClipboard(selectedText.unMask())

                        mode?.finish()
                        return true
                    }

                    android.R.id.cut -> {
                        val selectedText =
                            text.toString().subStringSafety(selectionStart, selectionEnd)

                        context.copyToClipboard(selectedText.unMask())
                        setText(text.toString().removeRange(selectionStart, selectionEnd))

                        mode?.finish()
                        return true
                    }
                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }
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

    fun doAfterTextChanged(action: (text: Editable?) -> Unit) {
        onTextChanged = {
            action.invoke(it)
        }
    }

    fun setMask(mask: String) {
        if (this.mask != mask) {
            this.mask = mask

            if (mask.trim().isEmpty()) {
                setText("")
            } else {
                text = text
            }

            setSelectionSafety(text.toString().length)
        }
    }

    fun setHideKeyboardWhenMaskComplete(hide: Boolean) {
        this.hideKeyboardWhenMaskComplete = hide
    }

    fun setMaskDividerColor(color: Int) {
        this.maskDividerColor = color
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
            var lengthAfter = 0
            var editIndex = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                lengthBefore = s.length
                editIndex = start
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!mask.contains("#")) {
                    return
                }

                lengthAfter = s.length
                removeTextChangedListener(this)

                val current = s.toString().replace(" ", "")
                var digits = current.filter { it.isDigit() }

                if (lengthBefore == s.length + 1 && mask.getOrNull(editIndex) != '#') {
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

                setSelectionSafety(
                    when {
                        abs(lengthAfter - lengthBefore) > 1 -> s.length
                        lengthAfter < lengthBefore -> mask.subStringSafety(0, editIndex + 1)
                            .indexOfLast { it == '#' }

                        else -> mask.indexOf('#', editIndex + 1)
                    }
                )

                if (hideKeyboardWhenMaskComplete && s.length == mask.length) {
                    hideKeyboard()
                }

                checkMaskDividerColor(s)

                onTextChanged.invoke(s)
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
}