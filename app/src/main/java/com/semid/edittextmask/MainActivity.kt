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
        maskUtil.config("+994 ", "##-###-##-##", false)
        maskUtil
        maskUtil.startWith(
            arrayListOf(
                "+994 50",
                "+994 51",
                "+994 55",
                "+994 60",
                "+994 70",
                "+994 77",
                "+994 99"
            )
        )
        maskUtil.setup(phoneEdt)
    }
}
