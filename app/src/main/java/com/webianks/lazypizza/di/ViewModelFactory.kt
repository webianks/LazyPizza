package com.webianks.lazypizza.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webianks.lazypizza.data.repository.FakeOrdersRepository
import com.webianks.lazypizza.ui.screens.HistoryViewModel


class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(FakeOrdersRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}