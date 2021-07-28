package com.semid.edittextmask;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.semid.maskedittext.MaskEditText;
import com.semid.maskedittext.MaskUtil;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextInputLayout til = findViewById(R.id.til);
        MaskEditText staticEdt = findViewById(R.id.staticEdt);

        MaskUtil maskUtil = new MaskUtil();
        maskUtil.config("", "###-###-#-##", false);
        maskUtil.setup(til.getEditText());


    }
}