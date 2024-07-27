package com.example.ghtk_coroutines.models

import kotlinx.coroutines.Job

data class Timer(
    var giay : Int = 0,
    var phut : Int = 0,
    var gio : Int = 0,
    var isRunning: Boolean = false,
    var job: Job? = null)
