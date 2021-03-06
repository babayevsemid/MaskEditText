package com.semid.edittextmask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.semid.maskedittext.MaskUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val maskUtil = MaskUtil()
        maskUtil.config("", "## ### ## ##", false)
        maskUtil.setup(phoneEdt)
    }
}
