package com.github.satoshun.example.sample

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.satoshun.example.sample.databinding.MainActBinding

class MainActivity : BaseActivity() {

  private lateinit var myAdapter: Adapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<MainActBinding>(this, R.layout.main_act)

    myAdapter = Adapter()
    with(binding.recycler) {
      this.adapter = myAdapter
      layoutManager = GridLayoutManager(context, 2)
    }

    binding.recycler.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
      override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val delegate = myAdapter.delegate ?: return false
        return delegate.onTouchEvent(e)
      }
    })
  }
}

class Adapter : RecyclerView.Adapter<MainViewHolder>() {
  var delegate: TouchDelegate? = null

  override fun getItemCount(): Int = 100

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return when (viewType) {
      0 -> MainViewHolder(inflater.inflate(R.layout.main_item2, parent, false))
      else -> MainViewHolder(inflater.inflate(R.layout.main_item2, parent, false))
    }
  }

  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    // todo it's heavy task?
    holder.target.doOnPreDraw { view ->
      holder.tooltip.translationX = view.x + 240
      holder.tooltip.translationY = view.y - 180

      if (position == 0) {
        val itemView = holder.itemView
        itemView.elevation = 1f

        holder.tooltip.setOnClickListener {
          Toast.makeText(it.context, "clicked tooltip", Toast.LENGTH_SHORT).show()
        }

        itemView.post {
          val offsetViewBounds = Rect()
          holder.tooltip.getHitRect(offsetViewBounds)

          (itemView as ViewGroup).offsetDescendantRectToMyCoords(
            holder.tooltip,
            offsetViewBounds
          )
          delegate = TouchDelegate(offsetViewBounds, holder.tooltip)
        }
      }
    }
  }

  override fun getItemViewType(position: Int): Int = position % 2
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val cardView = itemView.findViewById<CardView>(R.id.card)!!
  val tooltip = itemView.findViewById<View>(R.id.tooltip)!!
  val target = itemView.findViewById<View>(R.id.subTitle)!!
}
