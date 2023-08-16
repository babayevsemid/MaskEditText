package com.semid.edittextmask

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import com.semid.edittextmask.databinding.ActivityMainBinding
import com.semid.maskedittext.mask

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainVM>()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()


        viewModel.phoneNumber
            .observe(this) {
                Log.e("phoneNumber", it)
            }
    }
}
