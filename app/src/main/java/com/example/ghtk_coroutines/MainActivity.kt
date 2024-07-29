package com.example.ghtk_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ghtk_coroutines.adapters.TimeAdapter
import com.example.ghtk_coroutines.databinding.ActivityMainBinding
import com.example.ghtk_coroutines.models.Timer
import com.example.ghtk_coroutines.viewmodels.TimeViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timeViewModel: TimeViewModel by lazy {
        ViewModelProvider(this).get(TimeViewModel::class.java)
    }
    private lateinit var adapter: TimeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = TimeAdapter(timeViewModel)
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adapter
        timeViewModel.timers.observe(this, Observer { timers ->
            adapter.notifyDataSetChanged()
            Log.d("dfsd",timers.size.toString())
        })
        binding.btnAdd.setOnClickListener {
            timeViewModel.add()
        }
        binding.btnReset.setOnClickListener {
            timeViewModel.reset()
        }
    }
}
