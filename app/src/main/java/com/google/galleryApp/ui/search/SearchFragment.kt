package com.google.galleryApp.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.google.galleryApp.R
import com.google.galleryApp.databinding.FragmentSearchBinding
import com.google.galleryApp.ui.home.PhotoAdapter
import com.google.galleryApp.ui.home.PhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        val adapter = PhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter =
                adapter.withLoadStateHeaderAndFooter(header = PhotoLoadStateAdapter { adapter.retry() },
                    footer = PhotoLoadStateAdapter { adapter.retry() })
        }
        searchViewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        adapter.addLoadStateListener { loadState ->
            binding.apply {

                //refreshing the list with new data set
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            }
        }
        //setHasOptionsMenu(true)

        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }

        searchViewModel.currentQuery.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty())
                binding.recyclerView.scrollToPosition(0)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}