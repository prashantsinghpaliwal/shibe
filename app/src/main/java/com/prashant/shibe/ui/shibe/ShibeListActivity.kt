package com.prashant.shibe.ui.shibe

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bigsteptech.deazzle.common.gone
import com.bigsteptech.deazzle.common.visible
import com.prashant.shibe.common.LoaderState
import com.prashant.shibe.databinding.ActivityMainBinding
import com.prashant.shibe.ui.SpacesItemDecoration
import com.prashant.shibe.ui.main.PagedShibeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShibeListActivity : AppCompatActivity() {


    private val viewModel by viewModels<ShibeViewModel>()
    private lateinit var pagedAdapter: PagedShibeAdapter
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpUi()
        setUpObservers()
    }

    private fun setUpObservers() {

        viewModel.getPagedData().observe(this, { pagedList ->
            pagedList?.let {
                Log.v("PagingLogs", "data ${pagedList}")
                pagedAdapter.submitList(pagedList)
            }

        })

        viewModel.getLoaderState().observe(this, {

            it?.let {
                Log.v("PagingLogs", "LoaderState ${it}")
                when (it) {

                    LoaderState.LOADING -> {

                    }

                    LoaderState.DONE -> {
                        binding.progress.gone()
                    }

                    LoaderState.ERROR -> {
                        binding.progress.gone()
                    }

                }

            }

        })
    }

    private fun setUpUi() {
        with(binding) {
            pagedAdapter = PagedShibeAdapter()
            issueRecyclerView.apply {
                layoutManager = GridLayoutManager(this@ShibeListActivity, 2)
                this.adapter = pagedAdapter
                setHasFixedSize(true)
                addItemDecoration(SpacesItemDecoration(10))
            }

        }
    }
}