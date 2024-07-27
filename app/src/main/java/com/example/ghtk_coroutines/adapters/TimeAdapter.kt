package com.example.ghtk_coroutines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_coroutines.databinding.ActivityMainBinding
import com.example.ghtk_coroutines.databinding.TimeLayoutItemBinding
import com.example.ghtk_coroutines.models.Timer
import com.example.ghtk_coroutines.viewmodels.TimeViewModel

class TimeAdapter(private val viewModel : TimeViewModel):
    RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {
    private val timers = viewModel.timers.value
    class TimeViewHolder(val itemBinding: TimeLayoutItemBinding,private val viewModel: TimeViewModel) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(timer: Timer) {
            updateTimer(timer)
            if(timer.isRunning){
                itemBinding.btnStart.visibility = View.GONE
                itemBinding.btnResume.visibility = View.GONE
                itemBinding.btnPause.visibility = View.VISIBLE
                itemBinding.btnStop.visibility = View.VISIBLE
            }else{
                itemBinding.btnStart.visibility = View.VISIBLE
                itemBinding.btnResume.visibility = View.VISIBLE
                itemBinding.btnPause.visibility = View.GONE
            }
            itemBinding.btnStart.setOnClickListener {
                viewModel.start(timer)
                updateTimer(timer)
            }
            itemBinding.btnStop.setOnClickListener {
                viewModel.stop(timer)
                updateTimer(timer)
            }
            itemBinding.btnResume.setOnClickListener {
                viewModel.resume(timer)
                updateTimer(timer)
            }
            itemBinding.btnPause.setOnClickListener {
                viewModel.pause(timer)
                updateTimer(timer)
            }
            itemBinding.llItem.setOnLongClickListener {
                viewModel.delete(timer)
                true
            }

        }
        fun updateTimer(timer: Timer) {
            itemBinding.tvGiay.text = timer.giay.toString()
            itemBinding.tvPhut.text = timer.phut.toString()
            itemBinding.tvGio.text = timer.gio.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return  return TimeViewHolder(
            TimeLayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            ),viewModel
        )
    }

    override fun getItemCount(): Int {
        return timers!!.size
    }
    fun addTimer(timer: Timer) {
        timers!!.add(timer)
        notifyItemInserted(timers.size - 1)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val timer = timers?.get(position)
        timer?.let { holder.bind(it) }

    }

}