package com.farshatov.test.testapplication.ui.contactdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.farshatov.test.testapplication.R
import com.farshatov.test.testapplication.databinding.ContactdetailFragmentBinding
import com.farshatov.test.testapplication.databinding.MainFragmentBinding
import com.farshatov.test.testapplication.ui.contact.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailFragment: Fragment() {

    private lateinit var viewDataBinding: ContactdetailFragmentBinding

    private val viewModel by viewModels<ContactDetailViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.contactdetail_fragment, container, false)
        return ContactdetailFragmentBinding.bind(root).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this.viewLifecycleOwner
            it.executePendingBindings()
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString("key_contact")?.let {
           viewModel.refreshData(it)
        }
        arguments?.getString("name")?.let {
            viewModel.name.set(it)
        }
        arguments?.getString("photo")?.let {
            viewModel.photo.set(it)
        }
    }

}