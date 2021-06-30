package com.example.todoapp.ui.items

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.model.Item
import com.example.todoapp.databinding.RecylerViewItemBinding
import com.example.todoapp.utils.CategoryConstants
import com.example.todoapp.utils.DateUtils

class ItemAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            RecylerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ItemViewHolder(private val binding: RecylerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onItemClick(item)
                    }
                }
                completedCheckBox.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onItemCheckedClick(item, completedCheckBox.isChecked)
                    }
                }
            }
        }

        fun bind(item: Item) {
            with(binding) {
                val category = getCategoryValues(item.category, root.context)
                val circle = ContextCompat.getDrawable(root.context, R.drawable.ic_circle)
                if (circle != null) {
                    circle.colorFilter =
                        PorterDuffColorFilter(
                            Color.parseColor(category.first),
                            PorterDuff.Mode.MULTIPLY
                        )
                }
                if (DateUtils().isDateInPast(item.date)) {
                    itemDate.setTextColor(Color.RED)
                }
                completedCheckBox.isChecked = item.completed
                itemName.text = item.name
                itemCategory.text = category.second
                itemDate.text = DateUtils().formatDateToString(item.date)
                circleView.background = circle
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean =
            oldItem == newItem

    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
        fun onItemCheckedClick(item: Item, isChecked: Boolean)
    }

    private fun getCategoryValues(itemCategory: Int, context: Context): Pair<String, String> {
        return when (itemCategory) {
            CategoryConstants.work -> {
                Pair("#1976D2", context.resources.getString(R.string.work))
            }
            CategoryConstants.shopping -> {
                Pair("#AFB42B", context.resources.getString(R.string.shopping))
            }
            else -> {
                Pair("#455A64", context.resources.getString(R.string.other))
            }
        }
    }
}