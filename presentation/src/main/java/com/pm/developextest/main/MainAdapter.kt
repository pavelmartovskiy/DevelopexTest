package com.pm.developextest.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.pm.developextest.R
import com.pm.developextest.core.AbsHolder
import com.pm.developextest.databinding.RvMainItemBinding

class MainAdapter: ListAdapter<Item, ItemHolder>(Dc()) {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = ItemHolder(parent)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ItemHolder(parent: ViewGroup) : AbsHolder<RvMainItemBinding>(parent, R.layout.rv_main_item) {
    fun bind(item: Item) {
        dataBinding.model = item
    }
}

private class Dc : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(i1: Item, i2: Item) = i1.url == i2.url
    override fun areContentsTheSame(i1: Item, i2: Item) = i1 == i2
}