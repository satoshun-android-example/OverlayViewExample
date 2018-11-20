package com.github.satoshun.example.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.satoshun.example.sample.databinding.MainActBinding

class MainActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<MainActBinding>(this, R.layout.main_act)

    with(binding.recycler) {
      adapter = Adapter()
      layoutManager = GridLayoutManager(context, 2)
    }
  }
}

class Adapter : RecyclerView.Adapter<MainViewHolder>() {
  override fun getItemCount(): Int = 100

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return MainViewHolder(inflater.inflate(R.layout.main_item, parent, false))
  }

  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    holder.cardView.clipToOutline = false
  }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val cardView = itemView.findViewById<CardView>(R.id.card)
}
