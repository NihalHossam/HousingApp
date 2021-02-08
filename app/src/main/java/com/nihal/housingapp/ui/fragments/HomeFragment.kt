package com.nihal.housingapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nihal.housingapp.BR
import com.nihal.housingapp.R
import com.nihal.housingapp.databinding.FragmentHomeBinding
import com.nihal.housingapp.models.House
import com.nihal.housingapp.utils.Status
import com.nihal.housingapp.viewmodel.HouseViewModel
import com.nihal.housingapp.adapters.HousesAdapter
import com.nihal.housingapp.models.HousesResponse
import com.nihal.housingapp.ui.activities.MainActivity
import com.nihal.housingapp.utils.Constants.GONE
import com.nihal.housingapp.utils.Constants.VISIBLE
import com.nihal.housingapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment that displays the houses retrieved from the api.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var adapter: HousesAdapter
    private lateinit var houseViewModel: HouseViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeRecyclerView.setHasFixedSize(true)
        houseViewModel = (activity as MainActivity).houseViewModel
        binding.setVariable(BR.houseViewModel, houseViewModel)
        binding.lifecycleOwner = this
        initRecyclerView()
        setObserver()
        setClickListener()
        return binding.root
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
     * Observes the mutable live data changes. If any change occurs:
     * If data is successfully retrieved, the list is sent to the adapter.
     * If the data is being retrieved, a progress bar is shown.
     * If an error occurred when retrieving the data, a message about the error is displayed.
     */
    private fun setObserver() {

        houseViewModel.res.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    binding.apply {
                        paginationProgressBar.visibility = GONE
                        errorTextView.visibility = GONE
                        errorImage.visibility = GONE
                        retryButton.visibility = GONE
                        homeRecyclerView.visibility = VISIBLE
                    }
                    it.data.let {response->
                        if (response?.status == "success"){
                            response.houses?.let { it1 -> adapter.submitList(it1) }
                        }
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        paginationProgressBar.visibility = VISIBLE
                        homeRecyclerView.visibility = GONE
                    }
                }
                Status.ERROR -> {
                    showError(it)
                }
            }
        })
    }

    /**
     * Shows an error message when an error occurs when retrieving the data.
     * @param resource that has the message to be displayed.
     */
    private fun showError(resource: Resource<HousesResponse>) {
        binding.retryButton.setOnClickListener {
            houseViewModel.getHouses()
            binding.paginationProgressBar.visibility = VISIBLE
        }
        binding.apply {
            paginationProgressBar.visibility = GONE
            homeRecyclerView.visibility = GONE
            errorTextView.text = getString(R.string.errorMessage, resource.message)
            errorTextView.visibility = VISIBLE
            errorImage.visibility = VISIBLE
            retryButton.visibility = VISIBLE
        }
    }

    /**
     * Sends a bundle to the Details fragment with the house that was clicked from the list.
     */
    private fun setClickListener(){
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {  putSerializable("house", it)  }
            (activity as MainActivity).navController!!.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }
    }

}