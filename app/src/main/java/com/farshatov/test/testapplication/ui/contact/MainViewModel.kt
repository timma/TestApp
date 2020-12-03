package com.farshatov.test.testapplication.ui.contact

import androidx.databinding.ObservableArrayList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.farshatov.test.testapplication.BR
import com.farshatov.test.testapplication.R
import com.farshatov.test.testapplication.data.ContactDataSource
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import timber.log.Timber
import timber.log.Timber.d
import java.util.*


class MainViewModel @ViewModelInject constructor(
        private val contactDataSource: ContactDataSource
) : ViewModel() {

    var listener: OnItemClickListener? = null

    val itemViewModel = AsyncDiffObservableList(AsyncDifferConfig.Builder(object: DiffUtil.ItemCallback<ContactItemViewModel>() {
                override fun areItemsTheSame(oldItem: ContactItemViewModel, newItem: ContactItemViewModel): Boolean {
                    return oldItem == newItem
                }
                override fun areContentsTheSame(oldItem: ContactItemViewModel, newItem: ContactItemViewModel): Boolean {
                    return oldItem == newItem
                }
            })
            .build())

    val itemBinding: OnItemBind<ContactItemViewModel> = OnItemBind { itemBinding, position, item ->
        itemBinding.set(BR.viewModel, R.layout.contact_item)
                .bindExtra(BR.item, item.contact)
                .bindExtra(BR.listener, OnItemClickListener {
                    listener?.onItemClick(it)
                })
    }



    fun reloadList() {
            viewModelScope.launch {
                val data = getData()
                val list = data.map {
                    ContactItemViewModel(it)
                }.toMutableList()
                itemViewModel.update(list)
            }
        }

        private suspend fun getData() = contactDataSource.getContacts()


    }