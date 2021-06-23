package com.prashant.shibe.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bigsteptech.deazzle.common.gone
import com.bigsteptech.deazzle.common.visible
import com.prashant.shibe.common.Status
import com.prashant.shibe.databinding.ActivityMainBinding
import com.prashant.shibe.ui.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//
//    private val viewModel by viewModels<MainViewModel>()
//    private lateinit var pagedAdapter: PagedShibeAdapter
//    private val binding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        setUpUi()
//        setUpObservers()
//    }
//
//    private fun setUpObservers() {
//
//        viewModel.getPagedData()?.observe(this, { pagedList ->
//
//            pagedList?.let {
//                pagedAdapter.submitList(pagedList)
//            }
//            binding.progress.gone()
//        })
//
//        viewModel.getNetworkState().observe(this, {
//
//            when (it.status) {
//
//                Status.LOADING -> {
//                    binding.progress.visible()
//                }
//
//                Status.SUCCESS -> {
//                    binding.progress.gone()
//                }
//
//                Status.ERROR -> {
//                    Toast.makeText(this, it.throwable?.localizedMessage, Toast.LENGTH_LONG)
//                        .show()
//                    binding.progress.gone()
//                }
//
//            }
//        })
//
//    }
//
//    private fun setUpUi() {
//        with(binding) {
//            pagedAdapter = PagedShibeAdapter()
//            issueRecyclerView.apply {
//                layoutManager = GridLayoutManager(this@MainActivity, 2)
//                this.adapter = pagedAdapter
//                setHasFixedSize(true)
//                addItemDecoration(SpacesItemDecoration(10))
//            }
//
//        }
//    }
//}