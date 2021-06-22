package com.prashant.shibe.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashant.shibe.data.local.ShibeLocal
import com.prashant.shibe.databinding.ItemShibeBinding


class PagedShibeAdapter : PagedListAdapter<ShibeLocal,
        PagedShibeAdapter.ShibeViewHolder>(ShibeLocal.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShibeViewHolder {
        val binding = ItemShibeBinding.inflate(LayoutInflater.from(parent.context))
        return ShibeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShibeViewHolder, position: Int) {
        with(holder.binding) {
            model = getItem(position)
        }
        holder.binding.executePendingBindings()
    }

    class ShibeViewHolder(val binding: ItemShibeBinding) : RecyclerView.ViewHolder(binding.root)
}