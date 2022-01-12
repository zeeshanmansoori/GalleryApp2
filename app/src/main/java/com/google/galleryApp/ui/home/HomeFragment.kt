package com.google.galleryApp.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.google.galleryApp.R
import com.google.galleryApp.databinding.FragmentHomeBinding
import com.google.galleryApp.util.log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val adapter = PhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter =
                adapter.withLoadStateHeaderAndFooter(header = PhotoLoadStateAdapter { adapter.retry() },
                    footer = PhotoLoadStateAdapter { adapter.retry() })
        }
        homeViewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }



        adapter.addLoadStateListener { loadState ->
            binding.apply {
                //refreshing the list with new data set
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                if (loadState.source.refresh is LoadState.Error) {
                    Snackbar.make(
                        binding.recyclerView,
                        "cant load images",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Retry") {
                            adapter.retry()
                        }.show()

                }
            }
        }
        //setHasOptionsMenu(true)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnSearchClickListener {
            log("search on click")
        }

        searchView.setOnCloseListener {
            log("search on setOnCloseListener")
            false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private var Snackbar.isVisible: Boolean
        get() = isShown
        set(value) {
            if (value) {
                if (!isShown)
                    show()
            } else {
                if (isShown)
                    dismiss()
            }
        }


}