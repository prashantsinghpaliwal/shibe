package com.prashant.shibe.ui.shibe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.prashant.shibe.data.paging.ShibeRepository

class ShibeViewModel @ViewModelInject constructor(
    private val shibeRepository: ShibeRepository
) : ViewModel() {

    fun getPagedData() = shibeRepository.getPagedData()

    fun getLoaderState() = shibeRepository.getLoaderState()
}