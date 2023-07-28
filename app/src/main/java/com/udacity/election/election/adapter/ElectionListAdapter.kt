package com.udacity.election.election.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.election.network.models.Election
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.election.databinding.ElectionItemBinding
import androidx.recyclerview.widget.DiffUtil

class ElectionListAdapter(private val clickListener: ElectionListener)
    : ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }
}

class ElectionViewHolder private constructor(private val binding: ElectionItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Election, clickListener: ElectionItemClickListener) {
        binding.election = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) : ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ElectionListItemBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}

class ElectionItemClickListener(private val clickListener: (Election) -> Unit) {
    fun onClick(data: Election) = clickListener(data)
}