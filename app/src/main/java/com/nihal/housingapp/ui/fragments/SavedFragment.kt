package com.nihal.housingapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nihal.housingapp.BR
import com.nihal.housingapp.R
import com.nihal.housingapp.adapters.HousesAdapter
import com.nihal.housingapp.databinding.FragmentSavedBinding
import com.nihal.housingapp.models.House
import com.nihal.housingapp.ui.activities.MainActivity
import com.nihal.housingapp.viewmodel.HouseViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment that displays from the database the houses that were saved by the user.
 */
@AndroidEntryPoint
class SavedFragment : Fragment() {

    private lateinit var adapter: HousesAdapter
    private lateinit var houseViewModel: HouseViewModel
    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
        binding.homeRecyclerView.setHasFixedSize(true)
        houseViewModel = (activity as MainActivity).houseViewModel
        binding.setVariable(BR.houseViewModel, houseViewModel)
        binding.lifecycleOwner = this
        initRecyclerView()
        setObserver()
        setClickListener()
        setupSwipe()
        return binding.root
    }

    /**
     * Observes the mutable live data changes. If any change occurs, the list
     * is sent to the adapter.
     */
    private fun setObserver() {
        houseViewModel.getSavedHouses().observe(viewLifecycleOwner, Observer { houses ->
            adapter.submitList(houses)
        })
    }

    /**
     * Initializes the recycler view that displays the houses retrieved from the API.
     * The received list from the HouseViewModel is sent to the adapter.
     */
    private fun initRecyclerView() {
        adapter = HousesAdapter()
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeRecyclerView.adapter = adapter
    }

    /**
     * Setting the swipe on the recyclerview items.
     * Upon swipe, the house item is deleted from the database.
     */
    private fun setupSwipe() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return true }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentHouse = adapter.getHouseAtPosition(position)
                houseViewModel.deleteHouse(currentHouse)
                Toast.makeText(context, "House Removed.", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).apply { attachToRecyclerView(binding.homeRecyclerView) }
    }

    /**
     * Sends a bundle to the Details fragment with the house that was clicked from the list.
     */
    private fun setClickListener(){
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {  putSerializable("house", it)  }
            (activity as MainActivity).navController!!.navigate(R.id.action_savedFragment_to_detailsFragment, bundle)
        }
    }

}