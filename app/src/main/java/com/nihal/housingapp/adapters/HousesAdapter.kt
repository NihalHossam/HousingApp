package com.nihal.housingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nihal.housingapp.databinding.HouseItemBinding
import com.nihal.housingapp.models.House

/**
 * The Adapter creates ViewHolder objects as needed, and sets the data for those views.
 */
class HousesAdapter(): RecyclerView.Adapter<HousesAdapter.HouseViewHolder>(){

    private var onItemClickListener: ((House) -> Unit)? = null

    private val diffCallback = object : DiffUtil.ItemCallback<House>(){
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HouseItemBinding.inflate(inflater, parent, false)
        return HouseViewHolder(binding)
    }

    /**
     * Gets a list to set it to the RecyclerView Adapter.
     * @param list The list to be submitted.
     */
    fun submitList(list: List<House>?) = differ.submitList(list)

    /**
     * Returns a house object at a specific position.
     * @param position The position of the item within the adapter's data set.
     * @return A house at the specified position.
     */
    fun getHouseAtPosition(position: Int): House = differ.currentList[position]

    /**
     * Gets the total number of items in this adapter.
     * @return an integer that represents the number of items in adapter.
     */
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * Associates a viewholder with data.
     * @param holder The ViewHolder that represents the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val currentHouse = differ.currentList[position]
        holder.bind(currentHouse)
        //holder.itemView.setOnClickListener { clickListener.onHouseItemClick(currentHouse) }
        holder.itemView.setOnClickListener{
            onItemClickListener?.let{it(currentHouse)}
        }
    }

    /**
     * Holds specifications for the views in the RecyclerView item row.
     */
    inner class HouseViewHolder(val binding: HouseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(house: House) {
            binding.house = house
            binding.executePendingBindings()
        }
    }

    /*
     * Used for delegating item click events.
     */
    fun setOnItemClickListener(listener: (House) -> Unit){
        onItemClickListener = listener
    }

}