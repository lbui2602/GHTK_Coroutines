package com.example.ghtk_coroutines.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghtk_coroutines.models.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.timer

class TimeViewModel : ViewModel() {
    private val _timers = MutableLiveData<MutableList<Timer>>(mutableListOf())
    val timers: LiveData<MutableList<Timer>> = _timers

    fun add() {
        val newTimer = Timer()
        _timers.value?.add(newTimer)
        _timers.value=_timers.value
        start(newTimer)
    }
    fun delete(timer: Timer){
        pause(timer)
        _timers.value?.remove(timer)
        _timers.value=_timers.value
    }
    fun reset() {
        _timers.value?.forEach { timer ->
            timer.giay = 0
            timer.phut = 0
            timer.gio = 0
            timer.isRunning = false
            timer.job?.cancel()
            timer.job = null
        }
        _timers.postValue(_timers.value) // Sử dụng postValue để cập nhật LiveData từ luồng nền
    }
    fun allStart() {
        _timers.value?.forEach { timer ->
            start(timer)
        }
    }
    fun allPause() {
        _timers.value?.forEach { timer ->
            pause(timer)
        }
    }
    fun allContinue() {
        _timers.value?.forEach { timer ->
            resume(timer)
        }
    }


    fun start(timer: Timer) {
        if(!timer.isRunning){
            timer.isRunning = true
            timer.job = viewModelScope.launch(Dispatchers.Main) {
                while (timer.isRunning) {
                    delay(1000)
                    timer.giay++
                    if (timer.giay == 60) {
                        timer.giay = 0
                        timer.phut++
                        if (timer.phut == 60) {
                            timer.phut = 0
                            timer.gio++
                        }
                    }
                    _timers.value=_timers.value
                }
            }
        }
    }

    fun stop(timer: Timer) {
        timer.isRunning = false
        timer.gio=0
        timer.phut=0
        timer.giay=0
        timer.job?.cancel()
        timer.job = null
    }

    fun resume(timer: Timer) {
        if (!timer.isRunning) {
            start(timer)
        }
    }

    fun pause(timer: Timer) {
        timer.isRunning = false
    }
}
