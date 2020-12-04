package com.farshatov.test.testapplication.ui.contactdetail

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.farshatov.test.testapplication.BR
import com.farshatov.test.testapplication.R
import com.farshatov.test.testapplication.data.ContactDataSource
import com.farshatov.test.testapplication.ui.contact.ContactItemViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import timber.log.Timber

class ContactDetailViewModel @ViewModelInject constructor(
        private val contactDataSource: ContactDataSource
) : ViewModel() {

    val name = ObservableField<String>()



    val itemViewModel = AsyncDiffObservableList(AsyncDifferConfig.Builder(object: DiffUtil.ItemCallback<ContactDetailItemViewModel>() {
        override fun areItemsTheSame(oldItem: ContactDetailItemViewModel, newItem: ContactDetailItemViewModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ContactDetailItemViewModel, newItem: ContactDetailItemViewModel): Boolean {
            return oldItem == newItem
        }
    })
    .build())

    val itemBinding: OnItemBind<ContactDetailItemViewModel> = OnItemBind { itemBinding, position, item ->
        itemBinding.set(BR.viewModel, R.layout.contactdetail_item)
    }

    fun refreshData(id: String){
        viewModelScope.launch {
            val data = getDataList(id)
            val list = data.map {
                ContactDetailItemViewModel(it)
            }.toMutableList()
            itemViewModel.update(list)
        }
    }

    private suspend fun getDataList(id: String) = contactDataSource.getPhonesContact(id)
}