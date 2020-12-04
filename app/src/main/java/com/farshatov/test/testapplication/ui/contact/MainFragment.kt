package com.farshatov.test.testapplication.ui.contact

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farshatov.test.testapplication.R
import com.farshatov.test.testapplication.data.Contact
import com.farshatov.test.testapplication.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
@AndroidEntryPoint
class MainFragment : Fragment(), OnItemClickListener {

    private lateinit var viewDataBinding: MainFragmentBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        return MainFragmentBinding.bind(root).also {
            it.viewModel = viewModel
            viewModel.listener = this
            it.lifecycleOwner = this.viewLifecycleOwner
            it.executePendingBindings()
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.reloadList()

    }

    override fun onItemClick(item: Contact?) {
        val bundle = bundleOf("key_contact" to item?.id,
                    "name" to item?.name,
                    "photo" to item?.photo
                )
        findNavController().navigate(R.id.action_mainFragment_to_contactDetailFragment, bundle)
    }

}