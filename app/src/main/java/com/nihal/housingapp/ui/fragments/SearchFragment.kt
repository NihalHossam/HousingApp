package com.nihal.housingapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nihal.housingapp.BR
import com.nihal.housingapp.R
import com.nihal.housingapp.adapters.HousesAdapter
import com.nihal.housingapp.databinding.FragmentSearchBinding
import com.nihal.housingapp.models.House
import com.nihal.housingapp.ui.activities.MainActivity
import com.nihal.housingapp.utils.Constants.GONE
import com.nihal.housingapp.utils.Constants.VISIBLE
import com.nihal.housingapp.viewmodel.HouseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * A fragment where the user can search for houses by city or zipcode.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var adapter: HousesAdapter
    private lateinit var houseViewModel: HouseViewModel
    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.homeRecyclerView.setHasFixedSize(true)
        houseViewModel = (activity as MainActivity).houseViewModel
        binding.setVariable(BR.houseViewModel, houseViewModel)
        binding.lifecycleOwner = this
        initRecyclerView()
        setClickListener()
        setSearchBarListeners()
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
     * Sends a bundle to the Details fragment with the house that was clicked from the list.
     */
    private fun setClickListener() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("house", it) }
            (activity as MainActivity).navController!!.navigate(
                R.id.action_searchFragment_to_detailsFragment, bundle)
        }
    }

    /**
     * Sets listeners for the search bar that displays cancel or search icon,
     * takes user input for search,
     * and checks whether key pressed is enter from physical keyboard or onscreen keyboard.
     */
    private fun setSearchBarListeners() {
        binding.searchBar.setOnClickListener { startSearchVisibility() }
        binding.searchBar.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) startSearchVisibility()
        }
        binding.cancelIcon.setOnClickListener { v -> cancelSearchVisibility(v) }
        binding.searchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" key
                if ((event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)){
                    hideKeyboardFromView(v!!)
                    binding.searchBar.clearFocus()
                    return true
                }
                return false
            }
        })
        binding.searchBar.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                // If the action is "done" pressed on onscreen keyboard.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboardFromView(v!!)
                    binding.searchBar.clearFocus()
                    return true
                }
                return false
            }
        })
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                hideNoResults()
                if(!charSequence.toString().equals("")) {
                    showSearchResults(charSequence.toString().toLowerCase(Locale.ROOT))
                }else{
                    adapter.submitList(null)
                    binding.searchImage.visibility = VISIBLE
                }
            }
        })
    }

    /**
     * Shows the search results retrieved.
     * If no search results are found, noResults text will be shown.
     * @param address The address typed by the user in the search bar (either a city or zip code).
     */
    private fun showSearchResults(address: String) {
        houseViewModel.searchHouses(address)?.let {
            adapter.submitList(it)
            if(it.isEmpty()) showNoResults()
            else hideNoResults()
        }
    }

    /**
     * Hides the search icon and shows the cancel icon.
     */
    private fun startSearchVisibility() {
        binding.searchIcon.visibility = GONE
        binding.cancelIcon.visibility = VISIBLE
        binding.searchBar.isFocusable = true
        binding.searchBar.isFocusableInTouchMode = true
        binding.searchBar.requestFocus()
    }

    /**
     * Shows the cancel icon when user clicks on the search bar.
     * @param v The view to hide keyboard from.
     */
    private fun cancelSearchVisibility(v: View) {
        hideKeyboardFromView(v)
        binding.searchBar.text.clear()
        binding.cancelIcon.visibility = GONE
        binding.searchIcon.visibility = VISIBLE
        binding.noResultsText.visibility = GONE
        binding.searchLayout.clearFocus()
    }

    /**
     * Displays an image and no search found text if no matching results are found.
     */
    private fun showNoResults(){
        binding.searchImage.visibility = VISIBLE
        binding.noResultsText.visibility = VISIBLE
    }

    /**
     * Hides the image and no search found text if search is cancelled or a result is found.
     */
    private fun hideNoResults(){
        binding.searchImage.visibility = GONE
        binding.noResultsText.visibility = GONE
    }

    /**
     * Hides the keyboard when user is done with the search.
     * @param v The view to hide keyboard from.
     */
    private fun hideKeyboardFromView(v: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

}