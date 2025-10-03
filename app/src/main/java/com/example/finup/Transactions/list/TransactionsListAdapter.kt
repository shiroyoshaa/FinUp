package com.example.finup.Transactions.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finup.Transactions.model.DisplayItemUi
import com.example.finup.databinding.HeaderForAdapterBinding
import com.example.finup.databinding.ItemForHolderBinding


class TransactionsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<DisplayItemUi>()

    override fun getItemViewType(position: Int): Int {
        return list[position].getItemType()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderViewHolder(HeaderForAdapterBinding.inflate(LayoutInflater.from(parent.context)))
            1 -> ItemViewHolder(ItemForHolderBinding.inflate(LayoutInflater.from(parent.context)))
            else -> throw IllegalStateException("view type not found")
        }
    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(list[position] as DisplayItemUi.TransactionDate)
            is ItemViewHolder -> holder.bind(list[position] as DisplayItemUi.TransactionDetails)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun addItems(currentList: List<DisplayItemUi>) {
        list.clear()
        list.addAll(currentList)
        notifyDataSetChanged()
    }
}


class HeaderViewHolder(private val binding: HeaderForAdapterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(header: DisplayItemUi.TransactionDate) {
        binding.headerDateTextView.text = header.day
        binding.headerTotalSumTextView.text = header.totalSumByDay
    }
}

class ItemViewHolder(private val binding: ItemForHolderBinding) :

    RecyclerView.ViewHolder(binding.root) {
    fun bind(details: DisplayItemUi.TransactionDetails) {
        binding.itemNameTextView.text = details.name
        binding.itemSumTextView.text = details.sum.toString()
    }
}
